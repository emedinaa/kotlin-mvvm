package com.emedinaa.kotlinmvvm.di

import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

object Injection {

    private val museumDataSource:MuseumDataSource = MuseumRepository()
    private val museumViewModelFactory = ViewModelFactory(museumDataSource)

    fun providerRepository():MuseumDataSource{
        return museumDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return museumViewModelFactory
    }
}