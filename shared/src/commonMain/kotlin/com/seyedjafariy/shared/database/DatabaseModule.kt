package com.seyedjafariy.shared.database

import com.seyedjafariy.shuttle.database.MainDB
import org.koin.dsl.module

internal val databaseModule = module {
    single { DatabaseFactory.create(get()) }

    factory { get<MainDB>().launchesQueries }

    factory { get<MainDB>().companyInfoQueries }
}