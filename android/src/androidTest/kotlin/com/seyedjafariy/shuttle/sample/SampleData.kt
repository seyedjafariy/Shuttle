package com.seyedjafariy.shuttle.sample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.*
import com.seyedjafariy.shuttle.utils.StringState


fun createLaunchState(socialLinks: Set<LaunchState.SocialLink> = emptySet()) = LaunchState(
    "id",
    "launch",
    null,
    Icons.Default.Add,
    StringState(R.string.app_name),
    StringState(R.string.app_name),
    StringState(R.string.app_name),
    "0",
    socialLinks,
)

fun createLaunchFilterState() = LaunchFilterState(
    operationState = LaunchOperationState.Disabled,
    sortingState = DateSortingState.ASC,
    dateFilterState = DateFilterState.Off
)