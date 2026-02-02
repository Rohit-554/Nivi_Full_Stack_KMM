package io.jadu.nivi

import android.app.Application
import io.jadu.nivi.di.initKoin

class MyNivi : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}
