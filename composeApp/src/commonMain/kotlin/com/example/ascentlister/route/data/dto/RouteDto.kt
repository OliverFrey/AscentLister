package com.example.ascentlister.route.data.dto

import com.example.ascentlister.location.data.dto.LocationDto
import kotlinx.serialization.Serializable

@Serializable
data class RouteDto (
    val routeId: Int,
    val routeName: String,
    val grade: String,
    val location: LocationDto?
)