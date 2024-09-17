package com.testask.kiosktabletapp

import android.app.Application
import com.testask.kiosktabletapp.di.appModule
import com.testask.kiosktabletapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KioskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KioskApplication)
            modules(appModule, networkModule)
            printLogger()
        }
    }
}