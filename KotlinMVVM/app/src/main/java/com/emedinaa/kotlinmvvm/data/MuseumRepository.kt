package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.domain.Museum

/**
 * @author Eduardo Medina
 */
class MuseumRepository(private val dataSource: MuseumDataSource) {
    suspend fun fetchMuseums(): OperationResult<Museum> = dataSource.retrieveMuseums()
}