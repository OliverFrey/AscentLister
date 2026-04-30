package com.example.ascentlister.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ascentlister.route.presentation.route_list.RouteListScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.RouteGraph
        ){
            navigation<Routes.RouteGraph>(
                startDestination = Routes.RouteList
            ){
                composable<Routes.RouteList>(){
                    val viewModel = koinViewModel<RouteListViewModel>()
                    RouteListScreenRoot(
                        viewModel = viewModel,
                        onRouteClicked = { route ->
                            navController.navigate(Routes.RouteDetails(route.routeId))
                        }
                    )
                }
                composable<Routes.RouteDetails>{ entry ->
                    val args = entry.toRoute<Routes.RouteDetails>()

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center)
                    {
                        Text("Route Details: ${args.it}")
                    }
                }
            }

        }
    }
}