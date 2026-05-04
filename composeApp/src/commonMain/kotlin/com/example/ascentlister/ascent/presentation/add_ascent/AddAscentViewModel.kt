package com.example.ascentlister.ascent.presentation.add_ascent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.ascent.domain.AscentRepository
import com.example.ascentlister.core.domain.onSuccess
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddAscentViewModel(
    private val ascentRepository: AscentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddAscentState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null
    private var locationSearchJob: Job? = null

    fun onAction(action: AddAscentAction) {
        when (action) {
            is AddAscentAction.OnRouteNameChange -> {
                _state.update { it.copy(routeName = action.name, selectedRoute = null) }
                searchRoutes(action.name)
            }
            is AddAscentAction.OnGradeChange -> {
                _state.update { it.copy(grade = action.grade, selectedRoute = null) }
            }
            is AddAscentAction.OnLocationNameChange -> {
                _state.update { it.copy(
                    locationName = action.name,
                    selectedRoute = null,
                    selectedLocation = null
                ) }
                searchLocations(action.name)
            }
            is AddAscentAction.OnAreaNameChange -> {
                _state.update { it.copy(
                    areaName = action.name,
                    selectedRoute = null,
                    selectedLocation = null
                ) }
            }
            is AddAscentAction.OnCountryChange -> {
                _state.update { it.copy(
                    country = action.country,
                    selectedRoute = null,
                    selectedLocation = null
                ) }
            }
            is AddAscentAction.OnStyleChange -> {
                _state.update { it.copy(style = action.style) }
            }
            is AddAscentAction.OnAttemptsChange -> {
                _state.update { it.copy(attempts = action.attempts) }
            }
            is AddAscentAction.OnCommentsChange -> {
                _state.update { it.copy(comments = action.comments) }
            }
            is AddAscentAction.OnDateChange -> {
                _state.update { it.copy(date = action.date) }
            }
            is AddAscentAction.OnRouteSelected -> {
                _state.update { it.copy(
                    routeName = action.route.routeName,
                    grade = action.route.grade,
                    locationName = action.route.location.locationName,
                    areaName = action.route.location.locationAreaName,
                    country = action.route.location.locationCountry,
                    selectedRoute = action.route,
                    selectedLocation = action.route.location,
                    routeSuggestions = emptyList(),
                    locationSuggestions = emptyList()
                ) }
            }
            is AddAscentAction.OnLocationSelected -> {
                _state.update { it.copy(
                    locationName = action.location.locationName,
                    areaName = action.location.locationAreaName,
                    country = action.location.locationCountry,
                    selectedLocation = action.location,
                    selectedRoute = null,
                    locationSuggestions = emptyList()
                ) }
            }
            AddAscentAction.OnSaveClick -> {
                if (!_state.value.isSaving) {
                    saveAscent()
                }
            }
            AddAscentAction.OnBackClick -> {
                // Handled in UI
            }
        }
    }

    private fun searchRoutes(query: String) {
        searchJob?.cancel()
        if (query.length < 2) {
            _state.update { it.copy(routeSuggestions = emptyList()) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(300L)
            val results = ascentRepository.searchLocalRoutes(query)
            _state.update { it.copy(routeSuggestions = results) }
        }
    }

    private fun searchLocations(query: String) {
        locationSearchJob?.cancel()
        if (query.length < 2) {
            _state.update { it.copy(locationSuggestions = emptyList()) }
            return
        }
        locationSearchJob = viewModelScope.launch {
            delay(300L)
            val results = ascentRepository.searchLocalLocations(query)
            _state.update { it.copy(locationSuggestions = results) }
        }
    }

    private fun saveAscent() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            
            val currentState = _state.value
            
            val route = if (currentState.selectedRoute != null) {
                currentState.selectedRoute
            } else {
                // Double check if a route with these details already exists even if not explicitly "selected" from suggestions
                val existingRoute = ascentRepository.getRouteByDetails(
                    name = currentState.routeName,
                    grade = currentState.grade,
                    locName = currentState.locationName,
                    area = currentState.areaName,
                    country = currentState.country
                )
                
                if (existingRoute != null) {
                    existingRoute
                } else {
                    val location = currentState.selectedLocation
                        ?: ascentRepository.getLocationByDetails(
                            name = currentState.locationName,
                            area = currentState.areaName,
                            country = currentState.country
                        )
                        ?: Location(
                            locationId = ascentRepository.getNextLocationId(),
                            locationName = currentState.locationName,
                            locationAreaName = currentState.areaName,
                            locationCountry = currentState.country
                        )
                    Route(
                        routeId = ascentRepository.getNextRouteId(),
                        routeName = currentState.routeName,
                        grade = currentState.grade,
                        location = location
                    )
                }
            }
            
            val ascent = Ascent(
                ascentId = ascentRepository.getNextAscentId(),
                route = route,
                date = currentState.date,
                style = currentState.style,
                attempts = currentState.attempts.toIntOrNull() ?: 1,
                comments = currentState.comments
            )
            
            ascentRepository.saveAscent(ascent).onSuccess {
                _state.update { it.copy(isSaving = false, isSaved = true) }
            }
        }
    }
}
