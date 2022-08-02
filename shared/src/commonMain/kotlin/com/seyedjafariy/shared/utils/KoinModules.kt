package com.seyedjafariy.shared.utils

import com.seyedjafariy.shared.database.databaseModule
import com.seyedjafariy.shared.network.networkModule
import com.seyedjafariy.shared.repository.companyInfoRepositoryModule
import com.seyedjafariy.shared.repository.launchRepositoryModule

val mergedModules = listOf(
    networkModule,
    databaseModule,
    launchRepositoryModule,
    companyInfoRepositoryModule,
)