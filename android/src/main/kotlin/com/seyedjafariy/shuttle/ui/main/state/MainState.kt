package com.seyedjafariy.shuttle.ui.main.state

data class MainState(
    val info : CompanyInfoState?,
    val launches : List<LaunchState>,
    val launchFilterState: LaunchFilterState,
)