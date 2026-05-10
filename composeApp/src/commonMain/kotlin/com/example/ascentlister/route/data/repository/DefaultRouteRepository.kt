package com.example.ascentlister.route.data.repository

import com.example.ascentlister.ascent.data.local.AscentDao
import com.example.ascentlister.ascent.data.mappers.toAscent
import com.example.ascentlister.ascent.data.mappers.toAscentDto
import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.core.domain.map
import com.example.ascentlister.core.domain.onSuccess
import com.example.ascentlister.route.data.local.RouteDao
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.domain.RouteRepository
import kotlinx.coroutines.flow.Flow

class DefaultRouteRepository(
    private val remoteAscentDataSource: RemoteAscentDataSource,
    private val routeDao: RouteDao,
) : RouteRepository {
    override suspend fun getRouteById(id: Int): Route? {
        return routeDao.getRouteById(id)
    }

    override fun getAscentsByRouteId(id: Int): Flow<List<Ascent>> {
        return routeDao.getAscentsByRouteId(id)
    }

    override suspend fun syncRoutes(query: String): Result<Unit, DataError.Remote> {
        return remoteAscentDataSource
            .getAscents(query)
            .map { dtoList ->
                val ascents = dtoList.map { it.toAscent() }
                val routes = ascents.map { it.route }
                val locations = routes.map { it.location }
                routeDao.upsertLocations(locations)
                routeDao.upsertRoutes(routes)
                routeDao.upsertAscents(ascents)
            }
    }

    override fun getLocalRoutes(): Flow<List<Route>> {
        return routeDao.getRoutes()
    }

    override suspend fun searchLocalRoutes(query: String): List<Route> {
        return routeDao.searchRoutes(query)
    }

    override suspend fun uploadData(): Result<Unit, DataError.Remote> {
        val unsyncedAscents = routeDao.getUnsyncedAscents()
        if (unsyncedAscents.isEmpty()) return Result.Success(Unit)

        val ascentDtos = unsyncedAscents.map { it.toAscentDto() }

        return remoteAscentDataSource
            .uploadAscents(ascentDtos)
            .onSuccess {
                routeDao.markAscentsAsSynced(unsyncedAscents.map { it.ascentId })
                routeDao.markRoutesAsSynced(unsyncedAscents.map { it.route.routeId })
                routeDao.markLocationsAsSynced(unsyncedAscents.map { it.route.location.locationId })
            }
    }
}
