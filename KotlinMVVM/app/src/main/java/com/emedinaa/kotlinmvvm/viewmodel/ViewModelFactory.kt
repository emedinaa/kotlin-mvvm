package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
class ViewModelFactory(private val repository: MuseumRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MuseumViewModel(repository) as T
    }
}