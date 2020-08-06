package com.emedinaa.kotlinmvvm.fake

import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

/**
 * @author : Eduardo Medina
 */
class FakeErrorMuseumATRepository:MuseumDataSource {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Error(mockException)
    }
}