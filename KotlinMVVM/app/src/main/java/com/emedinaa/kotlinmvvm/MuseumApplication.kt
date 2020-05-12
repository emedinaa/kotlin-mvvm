package com.emedinaa.kotlinmvvm

import android.app.Application
import com.emedinaa.kotlinmvvm.di.Injection

class MuseumApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.setup(this)
    }
}