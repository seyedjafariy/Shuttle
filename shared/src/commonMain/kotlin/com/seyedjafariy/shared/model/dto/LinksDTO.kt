package com.seyedjafariy.shared.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksDTO(
    @SerialName("patch")
    val patch: PatchDTO,
    @SerialName("article")
    val article: String?,
    @SerialName("webcast")
    val webcast: String?,
    @SerialName("wikipedia")
    val wikipedia: String?,
)