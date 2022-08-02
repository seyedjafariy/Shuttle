@file:OptIn(InternalAPI::class)

package com.seyedjafariy.shuttle

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.logger.DefaultLogger
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.seyedjafariy.shared.repository.KeyValueStore
import com.seyedjafariy.shuttle.BuildConfig.DEBUG
import com.seyedjafariy.shuttle.database.MainDB
import com.seyedjafariy.shuttle.utils.PreferenceKeyValueStore
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.util.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dbDriverModule = module {
    factory {
        AndroidSqliteDriver(
            schema = MainDB.Schema,
            context = get(),
            name = "ShuttleXDatabase.db"
        )
    } binds arrayOf(SqlDriver::class)
}

val storesModule = module {
    single { LoggingStoreFactory(TimeTravelStoreFactory()) } binds arrayOf(StoreFactory::class)
}

val networkEngineModule = module {
    factory<HttpClientEngine> {
        OkHttpEngine(OkHttpConfig().apply {
            preconfigured = get()
        })
    }

    factory {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            DefaultLogger.log(message)
        }

        httpLoggingInterceptor.level = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        httpLoggingInterceptor
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(120, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(120, TimeUnit.SECONDS)// Write timeout
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
}

val preferenceModule = module {
    single { androidContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE) }

    factory<KeyValueStore> { PreferenceKeyValueStore(get()) }
}


private const val PREFERENCE_NAME = "APP_PREFERENCES"
