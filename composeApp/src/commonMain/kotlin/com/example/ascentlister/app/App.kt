package com.example.ascentlister.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.ascentlister.route.presentation.route_detailpage.RouteDetailScreenRoot
import com.example.ascentlister.route.presentation.route_list.RouteListScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel
import com.example.ascentlister.route.presentation.route_detailpage.RouteDetailViewModel
import com.example.ascentlister.ascent.presentation.add_ascent.AddAscentScreenRoot
import com.example.ascentlister.ascent.presentation.add_ascent.AddAscentViewModel
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
                        },
                        onAddClick = {
                            navController.navigate(Routes.AddAscent)
                        }
                    )
                }
                composable<Routes.RouteDetails>{
                    val viewModel = koinViewModel<RouteDetailViewModel>()
                    RouteDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Routes.AddAscent>{
                    val viewModel = koinViewModel<AddAscentViewModel>()
                    AddAscentScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }

        }
    }
}