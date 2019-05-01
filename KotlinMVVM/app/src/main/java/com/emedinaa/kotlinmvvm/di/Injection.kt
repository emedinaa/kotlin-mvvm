package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumRepository

object Injection {

    fun providerRepository():MuseumRepository{
        return MuseumRepository()//could be a singleton
    }
}