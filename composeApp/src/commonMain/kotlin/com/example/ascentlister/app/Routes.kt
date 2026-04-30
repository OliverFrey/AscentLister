package com.example.ascentlister.app

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object RouteGraph: Routes

    @Serializable
    data object RouteList: Routes

    @Serializable
    data class RouteDetails(val it: Int): Routes
}