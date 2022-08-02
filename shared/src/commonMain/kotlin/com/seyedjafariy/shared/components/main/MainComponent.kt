package com.seyedjafariy.shared.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.seyedjafariy.shared.components.DIComponent
import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.Data
import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.LaunchesLoadSpec
import com.seyedjafariy.shared.utils.asValue
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface MainComponent {

    val state: Value<State>

    fun update(spec: LaunchesLoadSpec)

    data class State(
        val companyInfo: Data<CompanyInfo?> = Data(null, true),
        val launches: Data<List<Launch>> = Data(emptyList(), true),
        val launchesLoadSpec: LaunchesLoadSpec = LaunchesLoadSpec(),
    )
}

class MainComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope, listOf(mainModule)), MainComponent {

    private val store: MainStore = instanceKeeper.getStore {
        get()
    }

    override val state: Value<MainComponent.State> =
        store.asValue()

    override fun update(spec: LaunchesLoadSpec) {
        store.accept(MainStore.Intent.Update(spec))
    }
}