package com.example.ascentlister

import RouteListScreenRoot
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.ascentlister.route.presentation.route_list.RouteListViewModel

@Composable
@Preview
fun App() {
    RouteListScreenRoot(
        viewModel = remember { RouteListViewModel() },
        onRouteClicked = {

        }
    )
}