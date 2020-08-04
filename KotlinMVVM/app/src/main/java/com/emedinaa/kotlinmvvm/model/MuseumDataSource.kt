package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationResult
import kotlinx.coroutines.flow.Flow

/**
 * @author : Eduardo Medina
 */
interface MuseumDataSource {
    fun retrieveMuseumsFlow():Flow<List<Museum>>
}