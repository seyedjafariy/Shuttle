package com.seyedjafariy.shared.components.main

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.seyedjafariy.shared.components.main.MainComponent.State
import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.Data
import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.LaunchesLoadSpec
import com.seyedjafariy.shared.repository.CompanyInfoRepository
import com.seyedjafariy.shared.repository.LaunchRepository
import com.seyedjafariy.shared.utils.Log
import com.seyedjafariy.shared.utils.exhaustive
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainStoreProvider(
    private val storeFactory: StoreFactory,
    private val repository: LaunchRepository,
    private val companyInfoRepository: CompanyInfoRepository,
) {

    fun provide(): MainStore =
        object : MainStore, Store<MainStore.Intent, State, Nothing> by storeFactory.create(
            name = "RoomsStore",
            initialState = State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
            bootstrapper = SimpleBootstrapper(Action.Init)
        ) {}

    private sealed interface Message {
        data class CompanyInfoLoaded(val info: Data<CompanyInfo?>) : Message
        data class LaunchLoaded(val launches: Data<List<Launch>>) : Message
        data class UpdateLoadSpec(val spec: LaunchesLoadSpec) : Message
    }

    private sealed interface Action {
        object Init : Action
    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<MainStore.Intent, Action, State, Message, Nothing>() {

        var launchesJob: Job? = null

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.Init -> {
                    loadCompanyInfo()
                    loadLaunches()
                }
            }.exhaustive
        }

        override fun executeIntent(intent: MainStore.Intent, getState: () -> State) {
            when (intent) {
                is MainStore.Intent.Update -> {
                    dispatch(Message.UpdateLoadSpec(intent.spec))
                    loadLaunches(intent.spec)
                }
            }.exhaustive
        }

        private fun loadCompanyInfo() {
            companyInfoRepository
                .observerCompanyInfo()
                .onEach {
                    dispatch(Message.CompanyInfoLoaded(it))
                }
                .catch {
                    Log.e(it) { "observing company info flow stopped" }
                }
                .launchIn(scope)
        }

        private fun loadLaunches() {
            loadLaunches(LaunchesLoadSpec())
        }

        private fun loadLaunches(spec: LaunchesLoadSpec) {
            launchesJob?.cancel()

            launchesJob = repository.observeLaunches(spec)
                .onEach {
                    dispatch(Message.LaunchLoaded(it))
                }
                .catch {
                    Log.e(it) { "observing launches flow stopped" }
                }
                .launchIn(scope)
        }
    }

    // The reducer processes the result and returns the new state
    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.CompanyInfoLoaded -> copy(companyInfo = msg.info)
                is Message.LaunchLoaded -> copy(launches = msg.launches)
                is Message.UpdateLoadSpec -> copy(launchesLoadSpec = msg.spec)
            }
    }
}