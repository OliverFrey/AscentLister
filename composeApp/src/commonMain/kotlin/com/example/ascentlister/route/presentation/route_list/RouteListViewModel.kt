package com.example.ascentlister.route.presentation.route_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascentlister.core.domain.onError
import com.example.ascentlister.core.domain.onSuccess
import com.example.ascentlister.core.presentation.toUiText
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.domain.RouteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RouteListViewModel(
    private val routeRepository: RouteRepository
): ViewModel() {

    private var cachedRoutes = emptyList<Route>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(RouteListState())
    val state = _state
        .onStart {
            if(cachedRoutes.isEmpty()){
                observeSearchQuery()
                observeLocalRoutes()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: RouteListAction){
            when (action) {
                is RouteListAction.OnRouteClicked -> {

                }

                is RouteListAction.OnSearchQueryChange -> {
                    _state.update {
                        it.copy(searchQuery = action.query)
                    }
                }

                is RouteListAction.OnTabSelected -> {
                    _state.update {
                        it.copy(selectedTabIndex = action.index)
                    }
                }

                is RouteListAction.OnAddClick -> {

                }

                is RouteListAction.OnSyncClicked -> {
                    syncRoutes()
                }

                is RouteListAction.OnUploadClicked -> {
                    uploadData()
                }

                RouteListAction.OnFilterClicked -> TODO()
            }
    }

    private fun observeLocalRoutes() {
        routeRepository
            .getLocalRoutes()
            .onEach { routes ->
                cachedRoutes = routes
                if(_state.value.searchQuery.isBlank()) {
                    _state.update { it.copy(searchResults = routes) }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun syncRoutes() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        routeRepository
            .syncRoutes(_state.value.searchQuery)
            .onError { error ->
                _state.update { it.copy(
                    errorMessage = error.toUiText(),
                    isLoading = false
                ) }
            }
            .onSuccess {
                _state.update { it.copy(isLoading = false) }
            }
    }

    private fun uploadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        routeRepository
            .uploadData()
            .onError { error ->
                _state.update { it.copy(
                    errorMessage = error.toUiText(),
                    isLoading = false
                ) }
            }
            .onSuccess {
                _state.update { it.copy(isLoading = false) }
            }
    }

    private fun observeSearchQuery(){
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce { 500L }
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update { it.copy(
                            errorMessage = null,
                            searchResults = cachedRoutes
                        )}
                    }
                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchRoutes(query)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchRoutes(query: String) = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val results = routeRepository.searchLocalRoutes(query)
            _state.update { it.copy(
                errorMessage = null,
                searchResults = results,
                isLoading = false
            ) }
        }
}
