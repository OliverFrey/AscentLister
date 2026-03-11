package com.example.ascentlister.route.presentation.route_list

import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import com.plcoding.bookpedia.core.presentation.UiText

data class RouteListState(
    val searchQuery: String = "",
    val searchResults: List<Route> = routes,
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

private val locations = (1..10).map {
    Location(
        locationId = it,
        locationName = "Location $it",
        locationAreaName = "Bern",
        locationCountry = "Switzerland"
    )
}

val routes = (1..100).map {
    Route(
        routeId = it,
        routeName = "Route $it",
        grade = "Grade $it",
        location = locations.random()
    )
}
