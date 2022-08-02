package com.seyedjafariy.shuttle.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

data class StringState(
    @StringRes val resource: Int,
    val args: List<Any> = emptyList(),
)

@Composable
fun StringState.toStringResource() =
    stringResource(id = resource, *args.toTypedArray())
