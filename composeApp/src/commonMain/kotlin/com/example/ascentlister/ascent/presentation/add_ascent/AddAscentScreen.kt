package com.example.ascentlister.ascent.presentation.add_ascent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ascentlister.composeapp.generated.resources.Res
import ascentlister.composeapp.generated.resources.arrow_back_24px
import ascentlister.composeapp.generated.resources.arrow_down_24px
import com.example.ascentlister.core.presentation.DarkBlue
import com.example.ascentlister.core.presentation.DesertWhite
import com.example.ascentlister.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddAscentScreenRoot(
    viewModel: AddAscentViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isSaved) {
        LaunchedEffect(Unit) {
            onBackClick()
        }
    }

    AddAscentScreen(
        state = state,
        onAction = { action ->
            if (action is AddAscentAction.OnBackClick) {
                onBackClick()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAscentScreen(
    state: AddAscentState,
    onAction: (AddAscentAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Ascent") },
                navigationIcon = {
                    IconButton(onClick = { onAction(AddAscentAction.OnBackClick) }) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_24px),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = DesertWhite,
                    navigationIconContentColor = DesertWhite
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(padding)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                color = DesertWhite,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Route Name with Suggestions
                    Box {
                        OutlinedTextField(
                            value = state.routeName,
                            onValueChange = { onAction(AddAscentAction.OnRouteNameChange(it)) },
                            label = { Text("Route Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (state.routeSuggestions.isNotEmpty()) {
                            Popup(alignment = Alignment.BottomStart) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    shadowElevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Column {
                                        state.routeSuggestions.forEach { route ->
                                            ListItem(
                                                headlineContent = { Text(route.routeName) },
                                                supportingContent = { Text("${route.grade} - ${route.location.locationName}") },
                                                modifier = Modifier.clickable {
                                                    onAction(AddAscentAction.OnRouteSelected(route))
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = state.grade,
                        onValueChange = { onAction(AddAscentAction.OnGradeChange(it)) },
                        label = { Text("Grade") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider()

                    OutlinedTextField(
                        value = state.locationName,
                        onValueChange = { onAction(AddAscentAction.OnLocationNameChange(it)) },
                        label = { Text("Location Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = state.areaName,
                        onValueChange = { onAction(AddAscentAction.OnAreaNameChange(it)) },
                        label = { Text("Area") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = state.country,
                        onValueChange = { onAction(AddAscentAction.OnCountryChange(it)) },
                        label = { Text("Country") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider()

                    // Style Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = state.style,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Style") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.arrow_down_24px),
                                        contentDescription = "Open Dropdown"
                                    )
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("o", "f", "rp").forEach { style ->
                                DropdownMenuItem(
                                    text = { Text(style) },
                                    onClick = {
                                        onAction(AddAscentAction.OnStyleChange(style))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = state.attempts,
                        onValueChange = { onAction(AddAscentAction.OnAttemptsChange(it)) },
                        label = { Text("Attempts") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = state.comments,
                        onValueChange = { onAction(AddAscentAction.OnCommentsChange(it)) },
                        label = { Text("Comments") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Button(
                        onClick = { onAction(AddAscentAction.OnSaveClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = SandYellow
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !state.isSaving
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(color = SandYellow, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Save Ascent")
                        }
                    }
                }
            }
        }
    }
}
