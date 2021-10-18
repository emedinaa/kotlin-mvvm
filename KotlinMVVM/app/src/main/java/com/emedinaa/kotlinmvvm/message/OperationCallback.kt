package com.emedinaa.kotlinmvvm.message

/**
 * @author Eduardo Medina
 */
interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}