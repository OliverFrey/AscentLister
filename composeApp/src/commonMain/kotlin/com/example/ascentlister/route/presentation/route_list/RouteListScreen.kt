package com.example.ascentlister.route.presentation.route_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ascentlister.composeapp.generated.resources.Res
import ascentlister.composeapp.generated.resources.add_24px
import ascentlister.composeapp.generated.resources.arrow_back_24px
import ascentlister.composeapp.generated.resources.arrow_forward_24px
import ascentlister.composeapp.generated.resources.download_24px
import ascentlister.composeapp.generated.resources.no_search_results
import ascentlister.composeapp.generated.resources.refresh_24px
import ascentlister.composeapp.generated.resources.route_list
import ascentlister.composeapp.generated.resources.search_24px
import ascentlister.composeapp.generated.resources.statistics
import ascentlister.composeapp.generated.resources.upload_24px
import com.example.ascentlister.location.domain.Location
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.route.presentation.route_list.components.RouteList
import com.example.ascentlister.route.presentation.route_list.components.RouteSearchBar
import com.example.ascentlister.core.presentation.DarkBlue
import com.example.ascentlister.core.presentation.DesertWhite
import com.example.ascentlister.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RouteListScreenRoot(
    viewModel: RouteListViewModel = koinViewModel(),
    onRouteClicked: (Route) -> Unit,
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RouteListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is RouteListAction.OnRouteClicked -> onRouteClicked(action.route)
                RouteListAction.OnAddClick -> onAddClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteListScreen(
    state: RouteListState,
    onAction: (RouteListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val routeListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        if(state.searchResults.isNotEmpty()) {
             routeListState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (state.selectedTabIndex != pagerState.currentPage) {
            onAction(RouteListAction.OnTabSelected(pagerState.currentPage))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                actions = {
                    IconButton(onClick = { onAction(RouteListAction.OnUploadClicked) }) {
                        Icon(
                            painter = painterResource(Res.drawable.upload_24px),
                            contentDescription = "Upload Data"
                        )
                    }
                    IconButton(onClick = { onAction(RouteListAction.OnSyncClicked) }) {
                        Icon(
                            painter = painterResource(Res.drawable.download_24px),
                            contentDescription = "Sync Data"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = DesertWhite,
                    actionIconContentColor = DesertWhite
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(RouteListAction.OnAddClick) },
                containerColor = DarkBlue,
                contentColor = SandYellow,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add_24px),
                    contentDescription = "Add Ascent"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RouteSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onAction(RouteListAction.OnSearchQueryChange(it))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                color = DesertWhite,
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PrimaryTabRow(
                        selectedTabIndex = state.selectedTabIndex,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .widthIn(max = 700.dp)
                            .fillMaxWidth(),
                        containerColor = DesertWhite,
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(state.selectedTabIndex),
                                color = SandYellow
                            )
                        }
                    ) {
                        Tab(
                            selected = state.selectedTabIndex == 0,
                            onClick = {
                                onAction(RouteListAction.OnTabSelected(0))
                            },
                            modifier = Modifier.weight(1f),
                            selectedContentColor = SandYellow,
                            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = stringResource(Res.string.route_list),
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Tab(
                            selected = state.selectedTabIndex == 1,
                            onClick = {
                                onAction(RouteListAction.OnTabSelected(1))
                            },
                            modifier = Modifier.weight(1f),
                            selectedContentColor = SandYellow,
                            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = stringResource(Res.string.statistics),
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) { pageIndex ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (pageIndex) {
                                0 -> {
                                    if (state.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        when {
                                            state.errorMessage != null -> {
                                                Text(
                                                    text = state.errorMessage.asString(),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                            state.searchResults.isEmpty() -> {
                                                Text(
                                                    text = stringResource(Res.string.no_search_results),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                            else -> {
                                                RouteList(
                                                    routes = state.searchResults,
                                                    onRouteClick = {
                                                        onAction(RouteListAction.OnRouteClicked(it))
                                                    },
                                                    modifier = Modifier.fillMaxSize(),
                                                    scrollState = routeListState
                                                )
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    Text(
                                        text = stringResource(Res.string.no_search_results),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val locations = (1..10).map {
    Location(
        locationId = it,
        locationName = "Location $it",
        locationAreaName = "Bern",
        locationCountry = "Switzerland"
    )
}

private val routes = (1..100).map {
    Route(
        routeId = it,
        routeName = "Route $it",
        grade = "Grade $it",
        location = locations.random()
    )
}

@Preview
@Composable
private fun RouteListScreenPreview() {
    RouteListScreen(
        state = RouteListState(
            searchResults = routes
        ),
        onAction = {}
    )
}
