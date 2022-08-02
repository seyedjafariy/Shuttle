package com.seyedjafariy.shared.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.seyedjafariy.shared.components.DIComponent
import com.seyedjafariy.shared.components.main.MainComponent
import com.seyedjafariy.shared.components.main.MainComponentImpl
import org.koin.core.scope.Scope

interface RootComponent {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Main(val component: MainComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope), RootComponent {

    private val router = router<Config, RootComponent.Child>(
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, RootComponent.Child>> = router.state

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(
                MainComponentImpl(
                    componentContext,
                    scope,
                )
            )
        }

    sealed interface Config : Parcelable {
        @Parcelize
        object Main : Config
    }
}



