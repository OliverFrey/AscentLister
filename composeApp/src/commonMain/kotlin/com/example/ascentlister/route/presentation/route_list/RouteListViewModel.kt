package com.example.ascentlister.route.presentation.route_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.core.domain.onError
import com.example.ascentlister.core.domain.onSuccess
import com.example.ascentlister.core.presentation.toUiText
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
    private val ascentRepository: AscentRepository
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

                is RouteListAction.OnAddButtonClicked -> {

                }

                RouteListAction.OnSyncClicked -> {
                    syncRoutes()
                }
            }
    }

    private fun observeLocalRoutes() {
        ascentRepository
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
        ascentRepository
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
            _state.update { it.copy(
                isLoading = true
            ) }

            ascentRepository
                .getAscents(query)
                .onSuccess { searchResults ->
                    _state.update { it.copy(
                        errorMessage = null,
                        searchResults = searchResults.map { it.route },
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(
                        searchResults = emptyList(),
                        errorMessage = error.toUiText()
                    ) }
                }
        }
}
