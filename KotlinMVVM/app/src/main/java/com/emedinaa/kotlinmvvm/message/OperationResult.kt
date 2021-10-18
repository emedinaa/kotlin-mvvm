package com.emedinaa.kotlinmvvm.message

/**
 * @author : Eduardo Medina
 * @see : https://phauer.com/2019/sealed-classes-exceptions-kotlin/
 */
sealed class OperationResult<out T> {
    data class Success<T>(val data: List<T>?) : OperationResult<T>()
    data class Error(val exception: Exception?) : OperationResult<Nothing>()
}