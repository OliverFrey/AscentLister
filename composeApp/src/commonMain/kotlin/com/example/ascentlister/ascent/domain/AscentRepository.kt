package com.example.ascentlister.ascent.domain

import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

interface AscentRepository {
    suspend fun getAscents(query: String): Result<List<Ascent>, DataError.Remote>
    suspend fun searchLocalLocations(query: String): List<Location>
    suspend fun getRouteByDetails(name: String, grade: String, locName: String, area: String, country: String): Route?
    suspend fun getLocationByDetails(name: String, area: String, country: String): Location?
    suspend fun saveAscent(ascent: Ascent): Result<Unit, DataError.Local>
    
    suspend fun getNextRouteId(): Int
    suspend fun getNextAscentId(): Int
    suspend fun getNextLocationId(): Int
}
