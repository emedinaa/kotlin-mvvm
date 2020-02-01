package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationResult

interface MuseumDataSource {
    suspend fun retrieveMuseums():OperationResult<Museum>
}