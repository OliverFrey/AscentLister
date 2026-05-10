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

    @Query("SELECT * FROM Ascent")
    fun getAscents(): Flow<List<Ascent>>

    @Query("SELECT MAX(ascentId) FROM Ascent")
    suspend fun getMaxAscentId(): Int?

    @Query("DELETE FROM Route")
    suspend fun deleteAllRoutes()
}
