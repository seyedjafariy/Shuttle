package com.seyedjafariy.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class QueryRequestDTO(
    @SerialName("options")
    val options : QueryOptionDTO,
    @SerialName("query")
    val queryMap : Map<String, JsonElement> = emptyMap(),
)