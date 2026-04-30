package com.example.ascentlister.core.data

import android.content.Context
import com.example.ascentlister.core.domain.SessionStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPreferencesSessionStorage(
    context: Context
) : SessionStorage {
    private val prefs = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    override suspend fun get(): AccessTokenResponse? {
        val json = prefs.getString("session_token", null) ?: return null
        return try {
            Json.decodeFromString<AccessTokenResponse>(json)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun set(session: AccessTokenResponse?) {
        if (session == null) {
            prefs.edit().remove("session_token").apply()
        } else {
            val json = Json.encodeToString(session)
            prefs.edit().putString("session_token", json).apply()
        }
    }
}
