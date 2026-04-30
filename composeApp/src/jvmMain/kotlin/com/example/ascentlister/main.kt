package com.example.ascentlister

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.ascentlister.app.App
import com.example.ascentlister.di.initKoin

fun main() = {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "AscentLister",
        ) {
            App()
        }
    }
}