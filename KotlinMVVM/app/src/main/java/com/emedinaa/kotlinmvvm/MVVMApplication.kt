package com.emedinaa.kotlinmvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MVVMApplication:Application() {

    override fun onCreate() {
        super.onCreate()
    }
}