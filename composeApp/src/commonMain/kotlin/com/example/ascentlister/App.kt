package com.example.ascentlister

import RouteListScreenRoot
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import ascentlister.composeapp.generated.resources.Res
import ascentlister.composeapp.generated.resources.compose_multiplatform
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