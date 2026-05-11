package com.example.ascentlister.ascent.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.data.local.RouteDao
import com.example.ascentlister.route.domain.Route

@Database(
    entities = [Route::class, Ascent::class, Location::class],
    version = 2
)
abstract class AscentDatabase : RoomDatabase() {
    abstract fun ascentDao(): AscentDao
    abstract fun routeDao(): RouteDao

    companion object {
        const val DB_NAME = "ascent.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("ALTER TABLE Location ADD COLUMN locationIsSynced INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE Route ADD COLUMN routeIsSynced INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE Route ADD COLUMN locationIsSynced INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE Ascent ADD COLUMN ascentIsSynced INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE Ascent ADD COLUMN routeIsSynced INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE Ascent ADD COLUMN locationIsSynced INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
