package com.example.ascentlister.route.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ascentlister.location.domain.Location

@Entity
data class Route(
    @PrimaryKey val routeId: Int,
    val routeName: String,
    val grade: String,
    @Embedded val location: Location,
    val routeIsSynced: Boolean = false
)
