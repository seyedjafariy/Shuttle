package com.seyedjafariy.shuttle.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.CompanyInfoState
import com.seyedjafariy.shuttle.ui.main.state.LaunchState
import com.seyedjafariy.shuttle.ui.main.state.MainState
import com.seyedjafariy.shuttle.utils.toStringResource

@Composable
fun MainScreenContent(modifier: Modifier, state: MainState, openLaunchOptions: (LaunchState) -> Unit) {
    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        item(KEY_COMPANY_TITLE) {
            Column {
                Text(stringResource(id = R.string.company_section_title), Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item(KEY_COMPANY_INFO) {
            Column {
                CompanyInfo(state.info)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item(KEY_LAUNCH_TITLE) {
            Column {
                Text(stringResource(id = R.string.launches_section_title), Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        launchItems(state.launches, openLaunchOptions)
    }
}

@Composable
private fun CompanyInfo(info: CompanyInfoState?) = Row {
    if (info == null) {
        CircularProgressIndicator()
    } else {
        Text(
            info.info.toStringResource(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun LazyListScope.launchItems(launches: List<LaunchState>, openLaunchOptions: (LaunchState) -> Unit) {
    items(
        launches.size,
        key = {
            launches[it].id
        }
    ) {
        LaunchRow(launches[it], openLaunchOptions)
    }
}

private const val KEY_COMPANY_TITLE = "KEY_COMPANY_TITLE"
private const val KEY_LAUNCH_TITLE = "KEY_LAUNCH_TITLE"
private const val KEY_COMPANY_INFO = "KEY_COMPANY_INFO"
