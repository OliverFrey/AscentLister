package com.example.ascentlister.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("refresh_expires_in")
    val refreshExpiresIn: Int? = null,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("scope")
    val scope: String? = null
)
