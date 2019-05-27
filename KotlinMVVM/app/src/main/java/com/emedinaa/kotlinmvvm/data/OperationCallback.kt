package com.emedinaa.kotlinmvvm.data

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}