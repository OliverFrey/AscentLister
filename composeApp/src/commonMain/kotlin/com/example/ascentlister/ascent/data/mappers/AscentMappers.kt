package com.example.ascentlister.ascent.data.mappers

import com.example.ascentlister.ascent.data.dto.AscentDto
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.route.data.mappers.toRoute
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

fun AscentDto.toAscent(): Ascent {
    return Ascent(
        ascentId = ascentId,
        route = route?.toRoute() ?: throw IllegalArgumentException("Route cannot be null"),
        date = try {
            LocalDate.parse(date).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
        } catch (e: Exception) {
            0L // Or handle error appropriately
        },
        style = style,
        attempts = attempts,
        comments = comments
    )
}
