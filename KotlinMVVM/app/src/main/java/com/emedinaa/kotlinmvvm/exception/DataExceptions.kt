package com.emedinaa.kotlinmvvm.exception

class ServiceException(message:String?) : Exception(message)
class EmptyListException : Exception()
class ConnectionException : Exception()