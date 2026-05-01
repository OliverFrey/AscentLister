package com.example.ascentlister.ascent.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ascentlister.route.domain.Route

@Entity
data class Ascent(
    @PrimaryKey val ascentId: Int,
    @Embedded val route: Route,
    val date: Long, // Room doesn't support GMTDate directly, storing as Long timestamp
    val style: String,
    val attempts: Int,
    val comments: String,
    val ascentIsSynced: Boolean = false
)
