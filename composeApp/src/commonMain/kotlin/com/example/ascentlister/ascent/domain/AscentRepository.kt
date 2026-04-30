package com.example.ascentlister.ascent.domain

import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result

interface AscentRepository {
    suspend fun getAscents(query: String): Result<List<Ascent>, DataError.Remote>
}