package com.example.ascentlister.ascent.domain

import com.example.ascentlister.route.domain.Route
import io.ktor.util.date.GMTDate

data class Ascent(
    val ascentId: Int,
    val route: Route,
    val date: GMTDate,
    val style: String,
    val attempts: Int,
    val comments: String
)
