package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MVVMModule {

    @Singleton
    @Provides
    fun providerRepository(): MuseumDataSource {
        return MuseumRepository()
    }
}