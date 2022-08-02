package com.seyedjafariy.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDTO(
    @SerialName("id")
    val id: String,
    @SerialName("date_utc")
    val dateUtc: String,
    @SerialName("links")
    val links: LinksDTO,
    @SerialName("name")
    val name: String,
    @SerialName("rocket")
    val rocket: RocketDTO,
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("upcoming")
    val upcoming: Boolean
)