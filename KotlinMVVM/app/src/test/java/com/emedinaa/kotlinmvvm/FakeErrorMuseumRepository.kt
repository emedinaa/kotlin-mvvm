package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.message.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
class FakeErrorMuseumRepository : MuseumRepository {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Error(mockException)
    }
}