package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationResult

/**
 * @author Eduardo Medina
 */
interface MuseumDataSource {
    suspend fun retrieveMuseums(): OperationResult<Museum>
}