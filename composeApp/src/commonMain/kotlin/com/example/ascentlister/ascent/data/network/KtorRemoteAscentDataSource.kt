package com.example.ascentlister.ascent.data.network

import com.example.ascentlister.BuildKonfig
import com.example.ascentlister.ascent.data.dto.AscentDto
import com.example.ascentlister.core.data.safeCall
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteAscentDataSource (
    private val httpClient: HttpClient
) : RemoteAscentDataSource {

    override suspend fun getAscents(
        query: String,
    ): Result<List<AscentDto>, DataError.Remote> {
        return safeCall<List<AscentDto>> {
            httpClient.get(
                urlString = "${BuildKonfig.BASE_URL}/Ascent"
            ) {
                parameter("q", query)
            }
        }
    }

    override suspend fun uploadAscents(ascents: List<AscentDto>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "${BuildKonfig.BASE_URL}/Ascent/batch"
            ) {
                contentType(ContentType.Application.Json)
                setBody(ascents)
            }
        }
    }
}
