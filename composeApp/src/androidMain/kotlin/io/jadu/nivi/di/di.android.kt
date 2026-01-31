package io.jadu.nivi.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val androidModule = module {
//    single { getDatabaseBuilder(androidContext()) }
//    single { createDataStore(androidContext()) }
//    single { LocalNotificationManager(androidContext()) }
}

actual fun platformModule(): Module = androidModule

fun initKoin(application: Application) {
    initKoin { koinApplication ->
        koinApplication.apply {
            androidContext(application)
        }
    }
}