package com.seyedjafariy.shuttle.ui.main.state

import com.seyedjafariy.shared.model.LaunchesLoadSpec
import kotlinx.datetime.LocalDate

fun LaunchesLoadSpec.toFilterState() = LaunchFilterState(
    dateFilterState = getDateFilterState(),
    operationState = getOperationState(),
    sortingState = getSortingState(),
)

private fun LaunchesLoadSpec.getSortingState() = when(this.sorting){
    LaunchesLoadSpec.Sort.ASC -> DateSortingState.ASC
    LaunchesLoadSpec.Sort.DESC -> DateSortingState.DESC
}

private fun LaunchesLoadSpec.getDateFilterState() =
    this.launchYear?.year?.toString()?.let { DateFilterState.On(it) } ?: DateFilterState.Off

private fun LaunchesLoadSpec.getOperationState() = when (this.operationStatus) {
    LaunchesLoadSpec.OperationStatus.All -> LaunchOperationState.Disabled
    LaunchesLoadSpec.OperationStatus.Success -> LaunchOperationState.ONLY_SUCCESSFUL
    LaunchesLoadSpec.OperationStatus.Failed -> LaunchOperationState.ONLY_FAILED
}

fun LaunchFilterState.toLoadSpec() = LaunchesLoadSpec(
    launchYear = getLaunchYear(),
    operationStatus = getOperationStatus(),
    sorting = getSorting(),
)

private fun LaunchFilterState.getLaunchYear() = when (this.dateFilterState) {
    DateFilterState.Off -> null
    is DateFilterState.On -> LocalDate(this.dateFilterState.date.toInt(), 1, 1)
}
private fun LaunchFilterState.getOperationStatus() = when(this.operationState){
    LaunchOperationState.Disabled -> LaunchesLoadSpec.OperationStatus.All
    LaunchOperationState.ONLY_SUCCESSFUL -> LaunchesLoadSpec.OperationStatus.Success
    LaunchOperationState.ONLY_FAILED -> LaunchesLoadSpec.OperationStatus.Failed
}

private fun LaunchFilterState.getSorting() = when(this.sortingState){
    DateSortingState.ASC -> LaunchesLoadSpec.Sort.ASC
    DateSortingState.DESC -> LaunchesLoadSpec.Sort.DESC
}
