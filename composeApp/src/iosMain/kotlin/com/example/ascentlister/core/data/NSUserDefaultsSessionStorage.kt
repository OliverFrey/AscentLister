package com.example.ascentlister.core.data

import com.example.ascentlister.core.domain.SessionStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

class NSUserDefaultsSessionStorage : SessionStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override suspend fun get(): AccessTokenResponse? {
        val json = userDefaults.stringForKey("session_token") ?: return null
        return try {
            Json.decodeFromString<AccessTokenResponse>(json)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun set(session: AccessTokenResponse?) {
        if (session == null) {
            userDefaults.removeObjectForKey("session_token")
        } else {
            val json = Json.encodeToString(session)
            userDefaults.setObject(json, "session_token")
        }
    }
}
