package com.example.ascentlister.ascent.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface AscentDao {
    @Upsert
    suspend fun upsertRoutes(routes: List<Route>)

    @Query("SELECT * FROM Route")
    fun getRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM Route WHERE routeName LIKE '%' || :query || '%'")
    suspend fun searchRoutes(query: String): List<Route>

    @Upsert
    suspend fun upsertAscents(ascents: List<Ascent>)

    @Query("SELECT * FROM Ascent")
    fun getAscents(): Flow<List<Ascent>>

    @Query("DELETE FROM Route")
    suspend fun deleteAllRoutes()
}
