package com.example.ascentlister.ascent.presentation.add_ascent

import com.example.ascentlister.route.domain.Route

sealed interface AddAscentAction {
    data class OnRouteNameChange(val name: String): AddAscentAction
    data class OnGradeChange(val grade: String): AddAscentAction
    data class OnLocationNameChange(val name: String): AddAscentAction
    data class OnAreaNameChange(val name: String): AddAscentAction
    data class OnCountryChange(val country: String): AddAscentAction
    data class OnStyleChange(val style: String): AddAscentAction
    data class OnAttemptsChange(val attempts: String): AddAscentAction
    data class OnCommentsChange(val comments: String): AddAscentAction
    data class OnDateChange(val date: Long): AddAscentAction
    data class OnRouteSelected(val route: Route): AddAscentAction
    data object OnSaveClick: AddAscentAction
    data object OnBackClick: AddAscentAction
}
