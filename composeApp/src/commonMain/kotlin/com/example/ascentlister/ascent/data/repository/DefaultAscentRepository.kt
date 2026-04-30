package com.example.ascentlister.ascent.data.repository

import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.core.domain.map
import com.example.ascentlister.ascent.data.mappers.toAscent
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.ascent.data.local.AscentDao
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

class DefaultAscentRepository(
    private val remoteAscentDataSource: RemoteAscentDataSource,
    private val ascentDao: AscentDao
): AscentRepository {
    override suspend fun getAscents(query: String) : Result<List<Ascent>, DataError.Remote>{
        return remoteAscentDataSource
            .getAscents(query)
            .map { dtoList -> dtoList.map { it.toAscent() } }
    }

    override suspend fun syncRoutes(query: String): Result<Unit, DataError.Remote> {
        return remoteAscentDataSource
            .getAscents(query)
            .map { dtoList ->
                val ascents = dtoList.map { it.toAscent() }
                val routes = ascents.map { it.route }
                ascentDao.upsertRoutes(routes)
                ascentDao.upsertAscents(ascents)
            }
    }

    override fun getLocalRoutes(): Flow<List<Route>> {
        return ascentDao.getRoutes()
    }

    override suspend fun searchLocalRoutes(query: String): List<Route> {
        return ascentDao.searchRoutes(query)
    }

    override suspend fun getRouteByDetails(name: String, grade: String, locName: String, area: String, country: String): Route? {
        return ascentDao.getRouteByDetails(name, grade, locName, area, country)
    }

    override suspend fun saveAscent(ascent: Ascent): Result<Unit, DataError.Local> {
        return try {
            ascentDao.upsertRoutes(listOf(ascent.route))
            ascentDao.upsertAscents(listOf(ascent))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
