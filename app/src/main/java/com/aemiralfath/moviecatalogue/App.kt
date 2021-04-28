package com.aemiralfath.moviecatalogue

import android.app.Application
import com.aemiralfath.moviecatalogue.di.appModule
import com.aemiralfath.moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}