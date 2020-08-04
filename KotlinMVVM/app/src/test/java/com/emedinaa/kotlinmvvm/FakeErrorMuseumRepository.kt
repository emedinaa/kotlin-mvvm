package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.exception.ServiceException
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author : Eduardo Medina
 */
class FakeErrorMuseumRepository:MuseumDataSource {

    private val mockException = ServiceException("Ocurrió un error")

    override fun retrieveMuseumsFlow(): Flow<List<Museum>> {
        return flow {
            throw mockException
        }
    }
}