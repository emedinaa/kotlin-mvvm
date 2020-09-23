package com.emedinaa.kotlinmvvm.data

/**
 * @author Eduardo Medina
 */
interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}