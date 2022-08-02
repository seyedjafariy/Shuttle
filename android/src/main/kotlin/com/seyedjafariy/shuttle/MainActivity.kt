package com.seyedjafariy.shuttle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.seyedjafariy.shared.components.root.RootComponent
import com.seyedjafariy.shared.components.root.RootComponentImpl
import com.seyedjafariy.shuttle.ui.root.RootScreen
import com.seyedjafariy.shuttle.ui.theme.ShuttleTheme
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = rootComponent(defaultComponentContext())

        setContent {
            ShuttleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(component)
                }
            }
        }
    }

    @OptIn(KoinInternalApi::class)
    private fun rootComponent(componentContext: ComponentContext): RootComponent =
        RootComponentImpl(
            componentContext = componentContext,
            parentScope = GlobalContext.get().scopeRegistry.rootScope,
        )
}

