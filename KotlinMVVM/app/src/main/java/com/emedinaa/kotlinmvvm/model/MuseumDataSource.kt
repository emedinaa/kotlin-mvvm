package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationCallback

interface MuseumDataSource {

    fun retrieveMuseums(callback: OperationCallback)
    fun cancel()
}