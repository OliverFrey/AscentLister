package com.example.ascentlister.route.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Upsert
    suspend fun upsertRoutes(routes: List<Route>)

    @Upsert
    suspend fun upsertAscents(ascents: List<Ascent>)

    @Upsert
    suspend fun upsertLocations(locations: List<Location>)

    @Query("SELECT * FROM Route ORDER BY routeName")
    fun getRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM Route WHERE routeId = :routeId")
    suspend fun getRouteById(routeId: Int): Route?

    @Query("SELECT * FROM Route WHERE routeName LIKE '%' || :query || '%'")
    suspend fun searchRoutes(query: String): List<Route>

    @Query("SELECT * FROM Ascent WHERE routeId = :routeId ORDER BY date DESC")
    fun getAscentsByRouteId(routeId: Int): Flow<List<Ascent>>

    @Query("SELECT * FROM Ascent WHERE ascentIsSynced = 0")
    suspend fun getUnsyncedAscents(): List<Ascent>

    @Query("UPDATE Ascent SET ascentIsSynced = 1 WHERE ascentId IN (:ascentIds)")
    suspend fun markAscentsAsSynced(ascentIds: List<Int>)

    @Query("UPDATE Route SET routeIsSynced = 1 WHERE routeId IN (:routeIds)")
    suspend fun markRoutesAsSynced(routeIds: List<Int>)

    @Query("UPDATE Location SET locationIsSynced = 1 WHERE locationId IN (:locationIds)")
    suspend fun markLocationsAsSynced(locationIds: List<Int>)
}