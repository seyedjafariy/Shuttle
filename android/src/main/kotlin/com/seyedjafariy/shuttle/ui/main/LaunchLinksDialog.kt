package com.seyedjafariy.shuttle.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.LaunchState

@Composable
fun LaunchSocialContent(socialLinks: Set<LaunchState.SocialLink>, onDismiss: () -> Unit, clickedLink: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {},
        title = {
            Text(stringResource(id = R.string.more_on_this_launch))
        },
        text = {
            Row(modifier = Modifier.wrapContentHeight(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                LinkIcon(socialLinks, clickedLink)
            }
        }
    )
}

@Composable
fun RowScope.LinkIcon(socialLinks: Set<LaunchState.SocialLink>, clickedLink: (String) -> Unit) {
    socialLinks.forEach {
        AsyncImage(
            model = it.icon,
            contentDescription = "Launch Social link",
            modifier = Modifier
                .weight(1F)
                .height(48.dp)
                .clickable {
                    clickedLink(it.link)
                }
        )
    }
}