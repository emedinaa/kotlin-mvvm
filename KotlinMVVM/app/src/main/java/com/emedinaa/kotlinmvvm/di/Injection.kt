package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
object Injection {
    private val dataSource = MuseumRemoteDataSource(ApiClient)
    private val museumRepository = MuseumRepository(dataSource)

    fun providerRepository() = museumRepository
}