package com.seyedjafariy.shuttle.ui.main.state

data class LaunchFilterState(
    val operationState : LaunchOperationState,
    val sortingState: DateSortingState,
    val dateFilterState: DateFilterState,
)