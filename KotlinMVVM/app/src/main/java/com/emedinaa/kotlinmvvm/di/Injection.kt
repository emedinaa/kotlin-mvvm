package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

object Injection {

    fun providerRepository():MuseumDataSource{
        return MuseumRepository()//could be a singleton
    }
}