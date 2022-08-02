package com.seyedjafariy.shared.model

import kotlinx.datetime.LocalDate

data class LaunchesLoadSpec(
    val launchYear: LocalDate? = null,
    val operationStatus: OperationStatus = OperationStatus.All,
    val sorting: Sort = Sort.ASC
) {

    enum class OperationStatus {
        All,
        Success,
        Failed,
        ;
    }

    enum class Sort {
        ASC,
        DESC,
        ;
    }
}