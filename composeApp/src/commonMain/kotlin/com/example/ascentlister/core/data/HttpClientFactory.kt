package com.example.ascentlister.core.data

import com.example.ascentlister.BuildKonfig
import com.example.ascentlister.core.domain.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    
    fun createAuthClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { println("AuthClient: $message") }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    fun create(engine: HttpClientEngine, sessionStorage: SessionStorage, authRepository: AuthRepository): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HttpClient: $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        println("Bearer: Loading tokens, found: ${info != null}")
                        info?.let {
                            BearerTokens(it.accessToken, "")
                        }
                    }
                    refreshTokens {
                        println("Bearer: Refreshing tokens...")
                        val result = authRepository.fetchToken()
                        if (result is com.example.ascentlister.core.domain.Result.Success) {
                            println("Bearer: Refresh success!")
                            sessionStorage.set(result.data)
                            BearerTokens(result.data.accessToken, "")
                        } else {
                            println("Bearer: Refresh failed!")
                            null
                        }
                    }
                    sendWithoutRequest { request ->
                        val isAuthRequest = request.url.toString().contains("protocol/openid-connect/token")
                        println("Bearer: Send without request for ${request.url.host}? ${!isAuthRequest}")
                        !isAuthRequest
                    }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
