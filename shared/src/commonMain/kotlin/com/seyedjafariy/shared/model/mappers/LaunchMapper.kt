package com.seyedjafariy.shared.model.mappers

import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.Rocket
import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shuttle.database.Launches
import kotlinx.datetime.*
import kotlin.time.Duration

fun Launches.toDomain() = Launch(
    id,
    name,
    youtube,
    article,
    wikipedia,
    small_image,
    large_image,
    Rocket(
        rocket_id,
        rocket_name,
        rocket_type
    ),
    Launch.SuccessState(successful),
    Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()),
    Clock.System.now().daysUntil(Instant.parse(date), TimeZone.currentSystemDefault()).toLong(),
    upcoming,
)

fun LaunchDTO.toDomain() = Launch(
    id,
    name,
    links.webcast,
    links.article,
    links.wikipedia,
    links.patch.small,
    links.patch.large,
    Rocket(
        rocket.id,
        rocket.name,
        rocket.type
    ),
    Launch.SuccessState(success),
    Instant.parse(dateUtc).toLocalDateTime(TimeZone.currentSystemDefault()),
    Clock.System.now().daysUntil(Instant.parse(dateUtc), TimeZone.currentSystemDefault()).toLong(),
    upcoming,
)