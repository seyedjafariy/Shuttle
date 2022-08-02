package com.seyedjafariy.shuttle.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.seyedjafariy.shared.components.main.MainComponent
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.*
import com.seyedjafariy.shuttle.utils.toStringResource

@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.subscribeAsState()
    MainScreen(state.toState(), component)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MainScreen(state: MainState, component: MainComponent) = Column {
    var showDialog by remember { mutableStateOf(false) }
    var showSocialDialog by remember { mutableStateOf(setOf<LaunchState.SocialLink>()) }

    Scaffold(
        topBar = {
            MainScreenTopAppBar {
                showDialog = true
            }
        }
    ) {
        MainScreenContent(Modifier.padding(it), state) {
            showSocialDialog = it.socialLinks
        }
    }

    val context = LocalContext.current
    if (showSocialDialog.isNotEmpty()) {
        LaunchSocialContent(
            showSocialDialog,
            onDismiss = {
                showSocialDialog = emptySet()
            },
        ) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            context.startActivity(intent)
        }
    }

    if (showDialog) {
        LaunchFilterDialog(
            state.launchFilterState,
            {
                showDialog = false
            }) { submitedState ->
            showDialog = false
            component.update(submitedState.toLoadSpec())
        }
    }
}


@Composable
private fun MainScreenContent(modifier: Modifier, state: MainState, openLaunchOptions: (LaunchState) -> Unit) {
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
private fun MainScreenTopAppBar(filterClicks: () -> Unit) {
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(stringResource(id = R.string.spacex), modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
            Icon(
                imageVector = Icons.Default.FilterList,
                "Filters",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = filterClicks)
            )
        }
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

@Composable
private fun LaunchRow(launch: LaunchState, openLaunchOptions: (LaunchState) -> Unit) = Column(
    Modifier
        .fillMaxWidth()
        .clickable(enabled = launch.socialLinks.isNotEmpty()) {
            openLaunchOptions(launch)
        }) {
    Spacer(Modifier.height(10.dp))

    LaunchContent(launch)

    Spacer(Modifier.height(10.dp))
    Divider(
        Modifier
            .fillMaxWidth()
            .height(1.dp), color = Color.Gray
    )
}

@Composable
private fun LaunchContent(launch: LaunchState) = Row(Modifier.fillMaxWidth()) {
    AsyncImage(
        model = launch.image,
        contentDescription = "launch small image",
        modifier = Modifier.size(48.dp),
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(id = R.drawable.ic_launcher_foreground),
    )

    Spacer(Modifier.width(5.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        LaunchDetails(Modifier.weight(1F), launch)
        LaunchSuccessState(Modifier.weight(.1F), launch.successIcon)
    }
}

@Composable
private fun LaunchSuccessState(modifier: Modifier, state: ImageVector) {
    Icon(state, contentDescription = "Launch state", modifier = modifier)
}


@Composable
private fun LaunchDetails(modifier: Modifier, launch: LaunchState) = Column(modifier.fillMaxWidth()) {
    Row(Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.mission), modifier.weight(1F))
        Text(launch.name, modifier = modifier.weight(1F), textAlign = TextAlign.Start)
    }

    Row(Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.date_time), modifier.weight(1F))
        Text(launch.dateValue.toStringResource(), modifier.weight(1F))
    }

    Row(Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.rocket), modifier.weight(1F))
        Text(
            launch.rocketValue.toStringResource(),
            modifier.weight(1F)
        )
    }

    Row(Modifier.fillMaxWidth()) {
        Text(launch.daysDistanceTitle.toStringResource(), modifier.weight(1F))
        Text(launch.fromTodayValue, modifier = modifier.weight(1F))
    }
}

private const val KEY_COMPANY_TITLE = "KEY_COMPANY_TITLE"
private const val KEY_LAUNCH_TITLE = "KEY_LAUNCH_TITLE"
private const val KEY_COMPANY_INFO = "KEY_COMPANY_INFO"
