package com.emedinaa.kotlinmvvm.message

import com.emedinaa.kotlinmvvm.model.Museum

/**
 * @author Eduardo Medina
 */
interface OperationCallback {
    fun onSuccess(obj: List<Museum>)
    fun onError(obj: Exception)
}