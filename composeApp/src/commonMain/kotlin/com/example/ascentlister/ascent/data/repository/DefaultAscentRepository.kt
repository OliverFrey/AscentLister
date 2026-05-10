package com.example.ascentlister.ascent.data.repository

import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.core.domain.map
import com.example.ascentlister.ascent.data.mappers.toAscent
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.ascent.data.local.AscentDao
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.data.local.RouteDao
import com.example.ascentlister.route.domain.Route
import kotlin.math.max

class DefaultAscentRepository(
    private val remoteAscentDataSource: RemoteAscentDataSource,
    private val ascentDao: AscentDao,
    private val routeDao: RouteDao
): AscentRepository {
    override suspend fun getAscents(query: String) : Result<List<Ascent>, DataError.Remote>{
        return remoteAscentDataSource
            .getAscents(query)
            .map { dtoList -> dtoList.map { it.toAscent() } }
    }

    override suspend fun searchLocalLocations(query: String): List<Location> {
        return ascentDao.searchLocations(query)
    }

    override suspend fun getRouteByDetails(name: String, grade: String, locName: String, area: String, country: String): Route? {
        return ascentDao.getRouteByDetails(name, grade, locName, area, country)
    }

    override suspend fun getLocationByDetails(name: String, area: String, country: String): Location? {
        return ascentDao.getLocationByDetails(name, area, country) 
            ?: ascentDao.getLocationFromRouteByDetails(name, area, country)
    }

    override suspend fun saveAscent(ascent: Ascent): Result<Unit, DataError.Local> {
        return try {
            routeDao.upsertLocations(listOf(ascent.route.location))
            routeDao.upsertRoutes(listOf(ascent.route))
            routeDao.upsertAscents(listOf(ascent))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun getNextRouteId(): Int {
        val maxFromRoute = ascentDao.getMaxRouteIdFromRoute() ?: 0
        val maxFromAscent = ascentDao.getMaxRouteIdFromAscent() ?: 0
        return max(maxFromRoute, maxFromAscent) + 1
    }

    override suspend fun getNextAscentId(): Int {
        return (ascentDao.getMaxAscentId() ?: 0) + 1
    }

    override suspend fun getNextLocationId(): Int {
        val maxFromLocation = ascentDao.getMaxLocationIdFromLocation() ?: 0
        val maxFromRoute = ascentDao.getMaxLocationIdFromRoute() ?: 0
        val maxFromAscent = ascentDao.getMaxLocationIdFromAscent() ?: 0
        return max(maxFromLocation, max(maxFromRoute, maxFromAscent)) + 1
    }
}
