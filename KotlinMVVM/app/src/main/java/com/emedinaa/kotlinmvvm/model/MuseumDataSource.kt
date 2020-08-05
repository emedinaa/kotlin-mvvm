package com.emedinaa.kotlinmvvm.model

import kotlinx.coroutines.flow.Flow

/**
 * @author : Eduardo Medina
 */
interface MuseumDataSource {
    fun retrieveMuseumsFlow():Flow<List<Museum>>
}