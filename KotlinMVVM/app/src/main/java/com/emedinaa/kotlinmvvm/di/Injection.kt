package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

/**
 * @author Eduardo Medina
 */
object Injection {

    private var museumDataSource: MuseumDataSource? = null
    private var museumRepository: MuseumRepository? = null
    private var museumViewModelFactory: ViewModelFactory? = null

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

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        museumViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = museumDataSource ?: createMuseumDataSource()
    private fun providerRepository() = museumRepository ?: createMuseumRepository()

    fun provideViewModelFactory() = museumViewModelFactory ?: createFactory()

    fun destroy() {
        museumDataSource = null
        museumRepository = null
        museumViewModelFactory = null
    }
}