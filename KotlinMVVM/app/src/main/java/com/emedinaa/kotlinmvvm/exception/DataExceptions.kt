package com.emedinaa.kotlinmvvm.exception

/**
 * @author : Eduardo Medina
 */
class ServiceException(message:String?) : Exception(message)
class EmptyListException : Exception()
class ConnectionException : Exception()