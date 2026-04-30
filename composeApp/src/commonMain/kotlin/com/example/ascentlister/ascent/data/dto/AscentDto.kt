package com.example.ascentlister.ascent.data.dto

import com.example.ascentlister.route.data.dto.RouteDto
import kotlinx.serialization.Serializable

@Serializable
data class AscentDto (
    val ascentId: Int,
    val route: RouteDto?,
    val date: String,
    val style: String,
    val attempts: Int,
    val comments: String
)