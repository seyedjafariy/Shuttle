package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.components.main.SpaceXAPI
import com.seyedjafariy.shared.components.main.SpaceXAPIImpl
import org.koin.dsl.module

val launchRepositoryModule = module {
    factory<SpaceXAPI> { SpaceXAPIImpl(get()) }
    factory { LaunchQueryDTOFactory() }
    factory<LaunchLocalDataSource> { LaunchLocalDataSourceImpl(get()) }
    factory<LaunchRepository> { LaunchRepositoryImpl(get(), get(), get(), get()) }
}

val companyInfoRepositoryModule = module {
    factory<CompanyInfoRepository> { CompanyInfoRepositoryImpl(get(), get()) }
}