package com.example.ascentlister.route.presentation.route_list

import com.example.ascentlister.route.domain.Route

sealed interface RouteListAction {
    data class OnSearchQueryChange(val query: String): RouteListAction
    data class OnRouteClicked(val route: Route): RouteListAction
    data class OnTabSelected(val index: Int): RouteListAction
    data object OnAddClick: RouteListAction
    data object OnSyncClicked: RouteListAction
    data object OnUploadClicked: RouteListAction
}
