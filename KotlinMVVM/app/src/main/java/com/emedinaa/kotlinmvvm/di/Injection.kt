package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author : Eduardo Medina
 */
object Injection {
    private val museumRepository = MuseumRepository()
    fun providerRepository():MuseumDataSource= museumRepository
}