package com.example.ascentlister.route.domain

import kotlinx.coroutines.flow.Flow
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result

interface RouteRepository {
    suspend fun getRouteById(id: Int): Route?
    fun getAscentsByRouteId(id: Int): Flow<List<Ascent>>
    suspend fun syncRoutes(query: String): Result<Unit, DataError.Remote>
    fun getLocalRoutes(): Flow<List<Route>>
    suspend fun searchLocalRoutes(query: String): List<Route>
    suspend fun uploadData(): Result<Unit, DataError.Remote>
}
