package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
object Injection {
    private var dataSource: MuseumDataSource? = null
    private var museumRepository: MuseumRepository? = null

    private fun createDataSource(): MuseumDataSource {
        val newDataSource = MuseumRemoteDataSource(ApiClient)
        dataSource = newDataSource
        return newDataSource
    }

    private fun createMuseumRepository(): MuseumRepository {
        val repository = MuseumRepository(provideDataSource())
        museumRepository = repository
        return repository
    }

    private fun provideDataSource() = dataSource ?: createDataSource()
    fun providerRepository() = museumRepository ?: createMuseumRepository()
}