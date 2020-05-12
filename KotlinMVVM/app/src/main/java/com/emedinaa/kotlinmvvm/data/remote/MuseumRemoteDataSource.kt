package com.emedinaa.kotlinmvvm.data.remote

import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum

interface MuseumRemoteDataSource {
    suspend fun retrieveMuseums():OperationResult<Museum>
}