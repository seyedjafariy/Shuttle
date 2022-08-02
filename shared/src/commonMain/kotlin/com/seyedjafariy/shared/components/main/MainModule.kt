package com.seyedjafariy.shared.components.main

import org.koin.dsl.module

val mainModule = module {
    factory { MainStoreProvider(get(), get(), get()).provide() }
}