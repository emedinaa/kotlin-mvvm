package com.emedinaa.kotlinmvvm.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
object Injection {
    private var museumRepository: MuseumRepository? = null

    private fun createMuseumRepository(): MuseumRepository {
        val dataSource = MuseumRemoteDataSource(ApiClient.build())
        museumRepository = dataSource
        return dataSource
    }

    fun providerRepository() = museumRepository ?: createMuseumRepository()

    fun destroy() {
        museumRepository = null
    }
}