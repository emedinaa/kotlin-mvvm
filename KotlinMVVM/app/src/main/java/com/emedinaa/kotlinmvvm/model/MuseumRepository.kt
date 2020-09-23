package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationResult

/**
 * @author Eduardo Medina
 */
class MuseumRepository(private val dataSource: MuseumDataSource) {

    suspend fun fetchMuseums(): OperationResult<Museum> = dataSource.retrieveMuseums()
}