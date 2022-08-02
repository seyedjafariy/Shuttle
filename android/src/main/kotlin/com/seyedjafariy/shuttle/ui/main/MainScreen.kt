package com.seyedjafariy.shuttle.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.seyedjafariy.shared.components.main.MainComponent
import com.seyedjafariy.shuttle.ui.main.state.LaunchState
import com.seyedjafariy.shuttle.ui.main.state.MainState
import com.seyedjafariy.shuttle.ui.main.state.toLoadSpec
import com.seyedjafariy.shuttle.ui.main.state.toState

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
