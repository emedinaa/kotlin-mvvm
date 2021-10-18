package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.message.OperationResult

interface MuseumRepository {
    suspend fun retrieveMuseums(): OperationResult<Museum>
}