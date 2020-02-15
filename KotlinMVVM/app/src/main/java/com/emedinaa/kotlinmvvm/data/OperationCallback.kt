package com.emedinaa.kotlinmvvm.data

interface OperationCallback<T> {
    fun onSuccess(data:List<T>?)
    fun onError(error:String?)
}