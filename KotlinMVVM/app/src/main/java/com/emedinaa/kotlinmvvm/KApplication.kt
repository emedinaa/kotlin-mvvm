package com.emedinaa.kotlinmvvm

import android.app.Application
import com.emedinaa.kotlinmvvm.di.appModule
import org.koin.core.context.startKoin

class KApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}