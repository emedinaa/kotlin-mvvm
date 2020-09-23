package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.domain.Museum

/**
 * @author Eduardo Medina
 */
interface MuseumDataSource {
    suspend fun retrieveMuseums(): OperationResult<Museum>
}