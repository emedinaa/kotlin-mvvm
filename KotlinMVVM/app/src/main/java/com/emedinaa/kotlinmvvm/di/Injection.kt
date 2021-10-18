package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.MuseumDataSource
import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
object Injection {
    private var museumDataSource: MuseumDataSource? = null
    private var museumRepository: MuseumRepository? = null

    private fun createMuseumDataSource(): MuseumDataSource {
        val dataSource = MuseumRemoteDataSource(ApiClient)
        museumDataSource = dataSource
        return dataSource
    }

    private fun createMuseumRepository(): MuseumRepository {
        val repository = MuseumRepository(provideDataSource())
        museumRepository = repository
        return repository
    }

    private fun provideDataSource() = museumDataSource ?: createMuseumDataSource()

    fun providerRepository() = museumRepository ?: createMuseumRepository()

    fun destroy() {
        museumDataSource = null
        museumRepository = null
    }
}