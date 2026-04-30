package com.example.ascentlister.location.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val locationId: Int,
    val locationName: String,
    val locationAreaName: String,
    val locationCountry: String,
)
