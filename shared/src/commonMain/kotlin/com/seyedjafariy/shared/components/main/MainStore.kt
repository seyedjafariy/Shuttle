package com.seyedjafariy.shared.components.main

import com.arkivanov.mvikotlin.core.store.Store
import com.seyedjafariy.shared.components.main.MainComponent.State
import com.seyedjafariy.shared.components.main.MainStore.Intent
import com.seyedjafariy.shared.model.LaunchesLoadSpec

interface MainStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class Update(
            val spec: LaunchesLoadSpec,
        ) : Intent
    }

}