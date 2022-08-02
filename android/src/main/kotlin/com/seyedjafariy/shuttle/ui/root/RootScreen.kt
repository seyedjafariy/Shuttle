package com.seyedjafariy.shuttle.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.fade
import com.seyedjafariy.shared.components.root.RootComponent
import com.seyedjafariy.shared.utils.exhaustive
import com.seyedjafariy.shuttle.ui.main.MainScreen

@Composable
fun RootScreen(component: RootComponent) {
    Children(
        component.routerState,
        animation = childAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Main -> MainScreen(child.component)
            //more screens can be added here
        }.exhaustive
    }
}
