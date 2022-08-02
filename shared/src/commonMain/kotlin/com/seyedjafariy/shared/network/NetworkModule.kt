package com.seyedjafariy.shared.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val networkModule = module {
    single {
        val engine: HttpClientEngine? = getOrNull()

        val clientConfig: HttpClientConfig<*>.() -> Unit = get()

        if (engine == null) {
            HttpClient(clientConfig)
        } else {
            HttpClient(engine, clientConfig)
        }
    }

    factory {
        Json {
            encodeDefaults = true
            isLenient = true
            allowSpecialFloatingPointValues = true
            allowStructuredMapKeys = true
            prettyPrint = false
            ignoreUnknownKeys = true
        }
    }

    factory<HttpClientConfig<*>.() -> Unit> {
        {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}
