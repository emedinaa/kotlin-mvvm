package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
object Injection {

    private var museumRepository: MuseumRepository? = null

    private fun createMuseumRepository(): MuseumRepository {
        val repository = MuseumRemoteDataSource()
        museumRepository = repository
        return repository
    }

    fun providerRepository(): MuseumRepository = museumRepository ?: createMuseumRepository()

    fun destroy() {
        museumRepository = null
    }
}