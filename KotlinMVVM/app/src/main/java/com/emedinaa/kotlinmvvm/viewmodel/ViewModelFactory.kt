package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

/**
 * @author : Eduardo Medina
 */
class ViewModelFactory(private val repository:MuseumDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =   MuseumViewModel(repository) as T
}