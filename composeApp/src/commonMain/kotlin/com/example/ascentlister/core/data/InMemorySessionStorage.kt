package com.example.ascentlister.core.data

import com.example.ascentlister.core.domain.SessionStorage

class InMemorySessionStorage : SessionStorage {
    private var session: AccessTokenResponse? = null

    override suspend fun get(): AccessTokenResponse? = session
    override suspend fun set(session: AccessTokenResponse?) {
        this.session = session
    }
}
