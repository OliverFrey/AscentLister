package com.example.ascentlister.ascent.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route

@Database(
    entities = [Route::class, Ascent::class, Location::class],
    version = 1
)
abstract class AscentDatabase : RoomDatabase() {
    abstract fun ascentDao(): AscentDao

    companion object {
        const val DB_NAME = "ascent.db"
    }
}
