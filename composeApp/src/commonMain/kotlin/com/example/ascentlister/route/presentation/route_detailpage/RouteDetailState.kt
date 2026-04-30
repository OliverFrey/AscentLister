package com.example.ascentlister.route.presentation.route_detailpage

import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.route.domain.Route

data class RouteDetailState(
    val route: Route? = null,
    val ascents: List<Ascent> = emptyList(),
    val isLoading: Boolean = true
)
