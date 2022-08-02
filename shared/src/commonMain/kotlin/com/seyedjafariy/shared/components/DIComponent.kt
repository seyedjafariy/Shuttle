package com.seyedjafariy.shared.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.scope.Scope

abstract class DIComponent(
    componentContext: ComponentContext,
    private val parentScope: Scope,
    private val modules: List<Module> = emptyList()
) : KoinScopeComponent, ComponentContext by componentContext {

    override val scope: Scope by lazy { createScope(this) }

    init {
        initDI()
    }

    private fun initDI() {
        scope.linkTo(parentScope)
        loadKoinModules(modules)

        lifecycle.doOnDestroy {
            unloadKoinModules(modules)
            scope.unlink(parentScope)
            scope.close()
        }
    }
}