package com.example.ascentlister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.ascentlister.route.presentation.route_list.components.RouteSearchBar

@Preview
@Composable
private fun RouteSearchBarPreview() {
    MaterialTheme{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ){
        RouteSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        }
    }
}