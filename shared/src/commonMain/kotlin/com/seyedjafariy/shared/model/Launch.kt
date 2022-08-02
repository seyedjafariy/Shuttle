package com.seyedjafariy.shared.model

import kotlinx.datetime.LocalDateTime

data class Launch(
    val id: String,
    val name: String,
    val youtube: String?,
    val article: String?,
    val wikipedia: String?,
    val smallImage: String?,
    val largeImage: String?,
    val rocket: Rocket,
    val successState: SuccessState,
    val date: LocalDateTime,
    val fromToday : Long,
    val upcoming: Boolean,
) {

    enum class SuccessState(
        val rawName: String,
    ) {
        NOT_LAUNCHED("not_launched"),
        SUCCESSFUL("successful"),
        FAILED("failed"),
        ;

        companion object {
            operator fun invoke(rawName: String) =
                values().first { it.rawName == rawName }

            operator fun invoke(triState: Boolean?) = when (triState) {
                true -> SUCCESSFUL
                false -> FAILED
                null -> NOT_LAUNCHED
            }
        }
    }
}