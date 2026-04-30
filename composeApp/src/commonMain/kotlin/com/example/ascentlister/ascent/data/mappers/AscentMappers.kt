package com.example.ascentlister.ascent.data.mappers

import com.example.ascentlister.ascent.data.dto.AscentDto
import com.example.ascentlister.ascent.domain.Ascent
import com.example.ascentlister.route.data.mappers.toRoute
import com.example.ascentlister.route.domain.Route

fun AscentDto.toAscent(): Ascent{
    return Ascent(
        ascentId = ascentId,
        route = route!!.toRoute(),
        date = date,
        style = style,
        attempts = attempts,
        comments = comments
    )
}
