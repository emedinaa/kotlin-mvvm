package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

object Injection {

    //MuseumRepository could be a singleton
    fun providerRepository():MuseumDataSource{
        return MuseumRepository()
    }
}