package com.seyedjafariy.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryResponseDTO<T : Any>(
    @SerialName("docs")
    val docs: List<T>
)