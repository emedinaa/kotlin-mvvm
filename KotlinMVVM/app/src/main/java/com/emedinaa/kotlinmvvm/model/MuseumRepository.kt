package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.MuseumDataSource
import com.emedinaa.kotlinmvvm.data.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Eduardo Medina
 */
class MuseumRepository(private val dataSource: MuseumDataSource) {

    suspend fun fetchMuseums(): OperationResult<Museum> = withContext(Dispatchers.IO) {
        dataSource.retrieveMuseums()
    }
}