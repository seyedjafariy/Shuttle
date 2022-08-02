package com.seyedjafariy.shared.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchDTO(
    @SerialName("large")
    val large: String?,
    @SerialName("small")
    val small: String?,
)