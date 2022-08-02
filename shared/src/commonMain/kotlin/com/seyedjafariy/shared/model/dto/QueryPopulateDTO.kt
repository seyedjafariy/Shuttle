package com.seyedjafariy.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryPopulateDTO(
    @SerialName("path")
    val path: String,
    @SerialName("select")
    val select: List<String>
)