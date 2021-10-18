package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

/**
 * @author Eduardo Medina
 */
object Injection {

    private var museumRepository: MuseumRepository? = null
    private var museumViewModelFactory: ViewModelFactory? = null

    private fun createMuseumRepository(): MuseumRepository {
        val repository = MuseumRemoteDataSource()
        museumRepository = repository
        return repository
    }

    private fun createViewModelFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        museumViewModelFactory = factory
        return factory
    }

    private fun providerRepository(): MuseumRepository =
        museumRepository ?: createMuseumRepository()

    fun provideViewModelFactory(): ViewModelFactory =
        museumViewModelFactory ?: createViewModelFactory()

    fun destroy() {
        museumRepository = null
        museumViewModelFactory = null
    }
}