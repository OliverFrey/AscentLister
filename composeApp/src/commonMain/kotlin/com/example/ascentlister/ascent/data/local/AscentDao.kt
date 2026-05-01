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

    @Upsert
    suspend fun upsertLocations(locations: List<Location>)

    @Query("SELECT * FROM Route")
    fun getRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM Route WHERE routeId = :routeId")
    suspend fun getRouteById(routeId: Int): Route?

    @Query("SELECT * FROM Route WHERE routeName LIKE '%' || :query || '%'")
    suspend fun searchRoutes(query: String): List<Route>

    @Query("SELECT * FROM Route WHERE routeName = :name AND grade = :grade AND locationName = :locName AND locationAreaName = :area AND locationCountry = :country LIMIT 1")
    suspend fun getRouteByDetails(name: String, grade: String, locName: String, area: String, country: String): Route?

    @Query("SELECT MAX(routeId) FROM Route")
    suspend fun getMaxRouteIdFromRoute(): Int?

    @Query("SELECT MAX(routeId) FROM Ascent")
    suspend fun getMaxRouteIdFromAscent(): Int?

    @Query("SELECT * FROM Location WHERE locationName LIKE '%' || :query || '%'")
    suspend fun searchLocations(query: String): List<Location>

    @Query("SELECT * FROM Location WHERE locationName = :name AND locationAreaName = :area AND locationCountry = :country LIMIT 1")
    suspend fun getLocationByDetails(name: String, area: String, country: String): Location?

    @Query("SELECT locationId, locationName, locationAreaName, locationCountry, locationIsSynced FROM Route WHERE locationName = :name AND locationAreaName = :area AND locationCountry = :country LIMIT 1")
    suspend fun getLocationFromRouteByDetails(name: String, area: String, country: String): Location?

    @Query("SELECT MAX(locationId) FROM Location")
    suspend fun getMaxLocationIdFromLocation(): Int?

    @Query("SELECT MAX(locationId) FROM Route")
    suspend fun getMaxLocationIdFromRoute(): Int?

    @Query("SELECT MAX(locationId) FROM Ascent")
    suspend fun getMaxLocationIdFromAscent(): Int?

    @Upsert
    suspend fun upsertAscents(ascents: List<Ascent>)

    @Query("SELECT * FROM Ascent")
    fun getAscents(): Flow<List<Ascent>>

    @Query("SELECT * FROM Ascent WHERE routeId = :routeId ORDER BY date DESC")
    fun getAscentsByRouteId(routeId: Int): Flow<List<Ascent>>

    @Query("SELECT MAX(ascentId) FROM Ascent")
    suspend fun getMaxAscentId(): Int?

    @Query("DELETE FROM Route")
    suspend fun deleteAllRoutes()

    @Query("SELECT * FROM Ascent WHERE ascentIsSynced = 0")
    suspend fun getUnsyncedAscents(): List<Ascent>

    @Query("UPDATE Ascent SET ascentIsSynced = 1 WHERE ascentId IN (:ascentIds)")
    suspend fun markAscentsAsSynced(ascentIds: List<Int>)

    @Query("UPDATE Route SET routeIsSynced = 1 WHERE routeId IN (:routeIds)")
    suspend fun markRoutesAsSynced(routeIds: List<Int>)

    @Query("UPDATE Location SET locationIsSynced = 1 WHERE locationId IN (:locationIds)")
    suspend fun markLocationsAsSynced(locationIds: List<Int>)
}
