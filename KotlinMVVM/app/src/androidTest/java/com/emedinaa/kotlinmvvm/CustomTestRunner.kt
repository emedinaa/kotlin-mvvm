package com.emedinaa.kotlinmvvm

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/training/dependency-injection/hilt-testing
 */
//java.lang.IllegalStateException: Hilt test, com.emedinaa.kotlinmvvm.MuseumActivityTest, cannot use a @HiltAndroidApp application but found com.emedinaa.kotlinmvvm.MVVMApplication. To fix, configure the test to use HiltTestApplication or a custom Hilt test application generated with @CustomTestApplication.
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
