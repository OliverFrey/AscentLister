package com.example.ascentlister.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ascentlister.ascent.data.local.AscentDatabase
import com.example.ascentlister.core.data.SharedPreferencesSessionStorage
import com.example.ascentlister.core.domain.SessionStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<SessionStorage> { SharedPreferencesSessionStorage(androidContext()) }
        single<AscentDatabase> {
            val dbFile = androidContext().getDatabasePath(AscentDatabase.DB_NAME)
            Room.databaseBuilder<AscentDatabase>(
                context = androidContext(),
                name = dbFile.absolutePath
            )
                .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
                .build()
        }
    }
