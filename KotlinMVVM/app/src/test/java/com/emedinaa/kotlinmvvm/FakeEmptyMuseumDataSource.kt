package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

/**
 * @author Eduardo Medina
 */
class FakeEmptyMuseumDataSource : MuseumDataSource {

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Success(emptyList())
    }
}