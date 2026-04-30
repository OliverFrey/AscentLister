package com.example.ascentlister.core.data

import com.example.ascentlister.core.domain.SessionStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileSessionStorage : SessionStorage {
    private val file = File(System.getProperty("user.home"), ".ascent_lister_session.json")

    override suspend fun get(): AccessTokenResponse? {
        if (!file.exists()) return null
        return try {
            Json.decodeFromString<AccessTokenResponse>(file.readText())
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun set(session: AccessTokenResponse?) {
        if (session == null) {
            if (file.exists()) file.delete()
        } else {
            file.writeText(Json.encodeToString(session))
        }
    }
}
