package com.seyedjafariy.shuttle

import android.app.Application
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.scope.Scope
import com.seyedjafariy.shared.utils.mergedModules

class App : Application() {

    lateinit var scope: Scope

    @OptIn(KoinInternalApi::class)
    override fun onCreate() {
        super.onCreate()

        TimeTravelServer().start()

        scope = startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dbDriverModule, storesModule, networkEngineModule, preferenceModule) + mergedModules)
        }.koin.scopeRegistry.rootScope
    }
}
