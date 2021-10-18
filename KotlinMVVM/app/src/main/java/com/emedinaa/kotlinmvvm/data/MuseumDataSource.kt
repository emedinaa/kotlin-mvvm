package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.model.Museum

/**
 * @author Eduardo Medina
 */
interface MuseumDataSource {
    suspend fun retrieveMuseums(): OperationResult<Museum>
}