package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.message.OperationCallback

/**
 * @author Eduardo Medina
 */
interface MuseumRepository {

    fun retrieveMuseums(callback: OperationCallback)
    fun cancel()
}