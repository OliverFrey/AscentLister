package com.example.ascentlister.ascent.data.repository

import com.example.ascentlister.ascent.data.network.RemoteAscentDataSource
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import com.example.ascentlister.core.domain.map
import com.example.ascentlister.ascent.data.mappers.toAscent
import com.example.ascentlister.ascent.domain.AscentRepository

class DefaultAscentRepository(
    private val remoteAscentDataSource: RemoteAscentDataSource
): AscentRepository {
    override suspend fun getAscents(query: String) : Result<List<Ascent>, DataError.Remote>{
        return remoteAscentDataSource
            .getAscents(query)
            .map { dtoList -> dtoList.map { it.toAscent() } }
    }
}
