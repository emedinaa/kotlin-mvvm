package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.data.remote.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDbRepository

class ViewModelFactory(private val remoteRepository: MuseumRemoteDataSource,
                       private val dbRepository: MuseumDbRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MuseumViewModel(remoteRepository,dbRepository) as T
    }
}