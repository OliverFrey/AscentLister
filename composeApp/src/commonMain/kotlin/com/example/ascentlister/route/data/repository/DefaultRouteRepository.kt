package com.example.ascentlister.route.data.repository

import com.example.ascentlister.ascent.data.local.AscentDao
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.domain.RouteRepository
import kotlinx.coroutines.flow.Flow

class DefaultRouteRepository(
    private val ascentDao: AscentDao
) : RouteRepository {
    override suspend fun getRouteById(id: Int): Route? {
        return ascentDao.getRouteById(id)
    }

    override fun getAscentsByRouteId(id: Int): Flow<List<Ascent>> {
        return ascentDao.getAscentsByRouteId(id)
    }
}
