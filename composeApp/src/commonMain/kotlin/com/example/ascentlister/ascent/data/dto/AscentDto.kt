package com.example.ascentlister.ascent.data.dto

import com.example.ascentlister.route.data.dto.RouteDto
import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable

@Serializable
data class AscentDto (
    val ascentId: Int,
    val route: RouteDto?,
    val date: Long,
    val style: String,
    val attempts: Int,
    val comments: String
)