package com.example.ascentlister.ascent.data.network

import com.example.ascentlister.ascent.data.dto.AscentDto
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result

interface RemoteAscentDataSource {
    suspend fun getAscents(
        query: String
    ): Result<List<AscentDto>, DataError.Remote>

    suspend fun uploadAscents(
        ascents: List<AscentDto>
    ): Result<Unit, DataError.Remote>
}
