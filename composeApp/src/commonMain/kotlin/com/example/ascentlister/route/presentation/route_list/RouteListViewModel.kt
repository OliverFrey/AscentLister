package com.example.ascentlister.route.presentation.route_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.core.domain.onSuccess
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RouteListViewModel(
    private val ascentRepository: AscentRepository
): ViewModel() {

    private var cachedRoutes = emptyList<Route>()

    private val _state = MutableStateFlow(RouteListState())
    val state = _state.asStateFlow()

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
                        searchRoutes(query)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchRoutes(query: String){
        _state.update { it.copy(
            isLoading = true
        ) }

        viewModelScope.launch {
            ascentRepository
                .getAscents(query)
                .onSuccess { searchResults ->
                    _state.update { it.copy(
                        errorMessage = null,
                        searchResults = searchResults,
                        errorMessage =
                    ) }
                }
        }
}