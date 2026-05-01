package com.example.ascentlister.ascent.presentation.add_ascent

import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.core.presentation.UiText
import kotlin.time.Clock

data class AddAscentState(
    val routeName: String = "",
    val grade: String = "",
    val locationName: String = "",
    val areaName: String = "",
    val country: String = "",
    val style: String = "rp",
    val attempts: String = "1",
    val comments: String = "",
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val selectedRoute: Route? = null,

    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val routeSuggestions: List<Route> = emptyList(),
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)
