package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

class FakeErrorMuseumRepository:MuseumDataSource {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Error(mockException)
    }
}