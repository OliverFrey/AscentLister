package com.example.ascentlister.di

import com.example.ascentlister.core.data.NSUserDefaultsSessionStorage
import com.example.ascentlister.core.domain.SessionStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<SessionStorage> { NSUserDefaultsSessionStorage() }
    }
