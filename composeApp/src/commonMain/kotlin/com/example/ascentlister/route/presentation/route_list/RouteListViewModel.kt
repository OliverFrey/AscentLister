package com.example.ascentlister.route.presentation.route_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RouteListViewModel: ViewModel() {

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
}