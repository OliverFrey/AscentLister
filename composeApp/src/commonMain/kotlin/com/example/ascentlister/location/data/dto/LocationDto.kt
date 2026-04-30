package com.example.ascentlister.location.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val locationId: Int,
    val locationName: String,
    val locationAreaName: String,
    val locationCountry: String
)