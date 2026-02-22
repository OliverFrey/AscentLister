package com.example.ascentlister

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform