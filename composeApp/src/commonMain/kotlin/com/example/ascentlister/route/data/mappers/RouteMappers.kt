package com.example.ascentlister.route.data.mappers

import com.example.ascentlister.location.data.mappers.toLocation
import com.example.ascentlister.route.data.dto.RouteDto
import com.example.ascentlister.route.domain.Route


fun RouteDto.toRoute(): Route {
    return Route(
        routeId = routeId,
        routeName = routeName,
        grade = grade,
        location = location!!.toLocation()
    )
}