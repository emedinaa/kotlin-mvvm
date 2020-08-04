package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.exception.EmptyListException
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author : Eduardo Medina
 */
class FakeEmptyMuseumRepository:MuseumDataSource {

    override fun retrieveMuseumsFlow(): Flow<List<Museum>> {
        return flow { throw EmptyListException() }
    }
}