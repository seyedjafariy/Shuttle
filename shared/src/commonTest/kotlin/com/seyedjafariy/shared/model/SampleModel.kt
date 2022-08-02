package com.seyedjafariy.shared.model

import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shared.model.dto.LinksDTO
import com.seyedjafariy.shared.model.dto.PatchDTO
import com.seyedjafariy.shared.model.dto.RocketDTO
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun createSampleLaunch() = Launch(
    "id",
    "name",
    null,
    null,
    null,
    null,
    null,
    Rocket(
        "rocketId",
        "rocket",
        "type"
    ),
    Launch.SuccessState.SUCCESSFUL,
    Instant.parse("2008-08-03T03:34:00.000Z").toLocalDateTime(TimeZone.UTC),
    1,
    true,
)

fun createCompanyInfo() = CompanyInfo(
    "company",
    "founder",
    2006,
    1000,
    1,
    10000
)

fun createSampleLaunchDTO() = LaunchDTO(
    "id",
    "2008-08-03T03:34:00.000Z",
    LinksDTO(
        PatchDTO(
            null,
            null
        ),
        null,
        null,
        null,
    ),
    "name",
    RocketDTO(
        "rocketId",
        "rocket",
        "rocketType"
    ),
    true,
    false,
)