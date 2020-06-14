package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationResult
import kotlinx.coroutines.flow.Flow

interface MuseumDataSource {
    suspend fun retrieveMuseums():OperationResult<Museum>
    fun retrieveMuseumsFlow():Flow<List<Museum>>
}