package com.emedinaa.kotlinmvvm.data.db

import androidx.lifecycle.LiveData

interface DbDataSource {
    fun museums(): LiveData<List<MuseumDTO>>
    suspend fun addMuseums(museums: List<MuseumDTO>)
    suspend fun deleteMuseums()
}