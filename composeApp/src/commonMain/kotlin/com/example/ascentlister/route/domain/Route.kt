package com.example.ascentlister.route.domain

import com.example.ascentlister.location.domain.Location

data class Route(
    val routeId: Int,
    val routeName: String,
    val grade: String,
    val location: Location,
)
