package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.message.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
class FakeEmptyMuseumRepository : MuseumRepository {

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Success(emptyList())
    }
}