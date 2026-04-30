package com.example.ascentlister.route.presentation.route_detailpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.ascentlister.app.Routes
import com.example.ascentlister.route.domain.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RouteDetailViewModel(
    private val routeRepository: RouteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val routeId = savedStateHandle.toRoute<Routes.RouteDetails>().it

    private val _state = MutableStateFlow(RouteDetailState())
    val state = _state
        .onStart {
            fetchRouteDetails()
            observeAscents()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun fetchRouteDetails() {
        viewModelScope.launch {
            val route = routeRepository.getRouteById(routeId)
            _state.update { it.copy(
                route = route,
                isLoading = false
            ) }
        }
    }

    private fun observeAscents() {
        viewModelScope.launch {
            routeRepository.getAscentsByRouteId(routeId).collect { ascents ->
                _state.update { it.copy(ascents = ascents) }
            }
        }
    }
}
