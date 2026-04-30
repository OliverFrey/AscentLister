package com.example.ascentlister.ascent.domain

import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

interface AscentRepository {
    suspend fun getAscents(query: String): Result<List<Ascent>, DataError.Remote>
    suspend fun syncRoutes(query: String): Result<Unit, DataError.Remote>
    fun getLocalRoutes(): Flow<List<Route>>
}
