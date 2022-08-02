package com.seyedjafariy.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryOptionDTO(
    @SerialName("pagination")
    val pagination : Boolean,
    @SerialName("select")
    val select : List<String>,
    @SerialName("sort")
    val sort : String,
    @SerialName("populate")
    val populate : List<QueryPopulateDTO>,
)