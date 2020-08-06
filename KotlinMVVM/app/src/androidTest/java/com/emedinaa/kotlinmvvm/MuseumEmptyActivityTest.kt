package com.emedinaa.kotlinmvvm

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.emedinaa.kotlinmvvm.view.MuseumActivity
import androidx.test.espresso.Espresso.onView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.emedinaa.kotlinmvvm.di.MVVMModule
import com.emedinaa.kotlinmvvm.fake.FakeEmptyMuseumATRepository
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

/**
 *  @author : Eduardo Medina
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@UninstallModules(MVVMModule::class)
@HiltAndroidTest
class MuseumEmptyActivityTest {

    //java.lang.IllegalStateException: The component was not created. Check that you have added the HiltAndroidRule.
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun displayLayoutEmptyWithFakeRepository(){
        val activityScenario = ActivityScenario.launch(MuseumActivity::class.java)
        onView(withId(R.id.layoutError)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.layoutEmpty)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(0)))

        activityScenario.close()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    object MVVMModule {

        @Singleton
        @Provides
        fun providerRepository(): MuseumDataSource {
            return FakeEmptyMuseumATRepository()
        }
    }
}