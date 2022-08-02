package com.seyedjafariy.shuttle.sample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.LaunchState
import com.seyedjafariy.shuttle.utils.StringState


fun createLaunchState(socialLinks: Set<LaunchState.SocialLink> = emptySet()) = LaunchState(
    "id",
    "launch",
    null,
    Icons.Default.Add,
    StringState(R.string.spacex),
    StringState(R.string.spacex),
    StringState(R.string.spacex),
    "0",
    socialLinks,
)