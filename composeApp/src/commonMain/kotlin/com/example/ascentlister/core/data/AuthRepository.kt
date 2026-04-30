package com.example.ascentlister.core.data

import com.example.ascentlister.BuildKonfig
import com.example.ascentlister.core.domain.DataError
import com.example.ascentlister.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters

class AuthRepository(
    private val authClient: HttpClient
) {
    suspend fun fetchToken(): Result<AccessTokenResponse, DataError.Remote> {
        return safeCall<AccessTokenResponse> {
            authClient.submitForm(
                url = BuildKonfig.KEYCLOAK_AUTH_URL,
                formParameters = parameters {
                    append("grant_type", "client_credentials")
                    append("client_id", BuildKonfig.KEYCLOAK_CLIENT_ID)
                    append("client_secret", BuildKonfig.KEYCLOAK_CLIENT_SECRET)
                }
            )
        }
    }
}
