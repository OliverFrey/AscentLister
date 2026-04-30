package com.example.ascentlister.ascent.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface AscentDao {
    @Upsert
    suspend fun upsertRoutes(routes: List<Route>)

    @Query("SELECT * FROM Route")
    fun getRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM Route WHERE routeId = :routeId")
    suspend fun getRouteById(routeId: Int): Route?

    @Query("SELECT * FROM Route WHERE routeName LIKE '%' || :query || '%'")
    suspend fun searchRoutes(query: String): List<Route>

    @Query("SELECT * FROM Route WHERE routeName = :name AND grade = :grade AND locationName = :locName AND locationAreaName = :area AND locationCountry = :country LIMIT 1")
    suspend fun getRouteByDetails(name: String, grade: String, locName: String, area: String, country: String): Route?

    @Query("SELECT * FROM Location WHERE locationName LIKE '%' || :query || '%'")
    suspend fun searchLocations(query: String): List<Location>

    @Upsert
    suspend fun upsertAscents(ascents: List<Ascent>)

    @Query("SELECT * FROM Ascent")
    fun getAscents(): Flow<List<Ascent>>

    @Query("SELECT * FROM Ascent WHERE routeId = :routeId ORDER BY date DESC")
    fun getAscentsByRouteId(routeId: Int): Flow<List<Ascent>>

    @Query("DELETE FROM Route")
    suspend fun deleteAllRoutes()
}
