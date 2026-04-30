package com.example.ascentlister.route.domain

import kotlinx.coroutines.flow.Flow
import com.example.ascentlister.ascent.domain.Ascent

interface RouteRepository {
    suspend fun getRouteById(id: Int): Route?
    fun getAscentsByRouteId(id: Int): Flow<List<Ascent>>
}
