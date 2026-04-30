package com.example.ascentlister.ascent.data.network

import com.example.ascentlister.ascent.data.dto.AscentDto
import com.example.ascentlister.core.data.safeCall
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "http://192.168.0.90:5164/api"

class KtorRemoteAscentDataSource (
    private val httpClient: HttpClient
) : RemoteAscentDataSource {

    override suspend fun getAscents(
        query: String,
    ): Result<List<AscentDto>, DataError.Remote> {
        return safeCall<List<AscentDto>> {
            httpClient.get(
                urlString = "${BASE_URL}/Ascent"
            ) {
                parameter("q", query)
            }
        }
    }
}