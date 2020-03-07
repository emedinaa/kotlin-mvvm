package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

object Injection {
    private val museumRepository = MuseumRepository()
    fun providerRepository():MuseumDataSource= museumRepository
}