package com.example.ascentlister.route.presentation.route_list

import com.example.ascentlister.route.domain.Route
import com.plcoding.bookpedia.core.presentation.UiText

data class RouteListState(
    val searchQuery: String = "",
    val searchResults: List<Route> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
