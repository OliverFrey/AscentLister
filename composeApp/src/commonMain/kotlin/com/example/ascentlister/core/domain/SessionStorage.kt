package com.example.ascentlister.core.domain

import com.example.ascentlister.core.data.AccessTokenResponse

interface SessionStorage {
    suspend fun get(): AccessTokenResponse?
    suspend fun set(session: AccessTokenResponse?)
}
