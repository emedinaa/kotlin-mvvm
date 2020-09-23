package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.remote.ApiClient
import com.emedinaa.kotlinmvvm.data.remote.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.data.MuseumRepository
import com.emedinaa.kotlinmvvm.domain.GetMuseumsUseCase

/**
 * @author Eduardo Medina
 */
object Injection {

    private val dataSource =
        MuseumRemoteDataSource(ApiClient)
    private val museumRepository =
        MuseumRepository(dataSource)

    fun providerRepository() = museumRepository

    fun provideMuseumUseCase() = GetMuseumsUseCase(museumRepository)
}