package com.seyedjafariy.shuttle.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.LaunchState
import com.seyedjafariy.shuttle.utils.toStringResource

@Composable
fun LaunchRow(launch: LaunchState, openLaunchOptions: (LaunchState) -> Unit) = Column(
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
