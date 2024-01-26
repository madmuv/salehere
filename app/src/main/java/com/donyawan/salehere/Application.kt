package com.donyawan.salehere

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Contextor.setContext(this)

        startKoin {
            androidLogger()
            androidContext(this@Application)
            koin.loadModules(listOf(appModule))
            koin.createRootScope()
        }
    }
}