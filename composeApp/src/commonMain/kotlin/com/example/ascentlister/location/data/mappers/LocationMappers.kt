package com.example.ascentlister.location.data.mappers

import com.example.ascentlister.location.data.dto.LocationDto
import com.example.ascentlister.location.domain.Location


fun LocationDto.toLocation(): Location {
    return Location(
        locationId = locationId,
        locationName = locationName,
        locationAreaName = locationAreaName,
        locationCountry = locationCountry,
        locationIsSynced = true
    )
}

fun Location.toLocationDto(): LocationDto {
    return LocationDto(
        locationId = locationId,
        locationName = locationName,
        locationAreaName = locationAreaName,
        locationCountry = locationCountry,
        locationStatus = 1
    )
}
