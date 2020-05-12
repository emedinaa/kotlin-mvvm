package com.emedinaa.kotlinmvvm.di

import android.content.Context
import com.emedinaa.kotlinmvvm.data.db.DbDataSource
import com.emedinaa.kotlinmvvm.data.remote.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDbDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDbRepository
import com.emedinaa.kotlinmvvm.model.MuseumRemoteRemoteRepository

object Injection {

    private val museumRepository = MuseumRemoteRemoteRepository()

    private lateinit var dbDataSource:DbDataSource
    private lateinit var museumDbRepository:MuseumDbRepository

    fun setup(context:Context){
        dbDataSource = MuseumDbDataSource(context)
        museumDbRepository = MuseumDbRepository(dbDataSource)
    }

    fun providerDBRepository(): MuseumDbRepository = museumDbRepository
    fun providerRemoteRepository(): MuseumRemoteDataSource = museumRepository
}