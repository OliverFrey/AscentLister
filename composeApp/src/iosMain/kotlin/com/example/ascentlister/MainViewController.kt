package com.example.ascentlister

import androidx.compose.ui.window.ComposeUIViewController
import com.example.ascentlister.app.App
import com.example.ascentlister.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }