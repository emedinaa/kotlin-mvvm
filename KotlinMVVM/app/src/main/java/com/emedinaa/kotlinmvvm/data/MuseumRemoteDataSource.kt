package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

/**
 * @author Eduardo Medina
 */
class MuseumRemoteDataSource(apiClient: ApiClient) : MuseumDataSource {

    private val service = apiClient.build()

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        try {
            val response = service?.museums()
            response?.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()?.data
                    OperationResult.Success(data)
                } else {
                    val message = it.body()?.msg
                    OperationResult.Error(Exception(message))
                }
            } ?: run {
                return OperationResult.Error(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}