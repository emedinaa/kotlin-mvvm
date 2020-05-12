package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.remote.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.data.remote.MuseumRemoteDataSource

class MuseumRemoteRemoteRepository: MuseumRemoteDataSource {

    override suspend fun retrieveMuseums():OperationResult<Museum> {
        try {
            val response = ApiClient.build()?.museums()
            response?.let {
                return if(it.isSuccessful && it.body()!=null){
                    val data = it.body()?.data
                    OperationResult.Success(data)
                }else{
                    val message = it.body()?.msg
                    OperationResult.Error(Exception(message))
                }
            }?:run{
                return OperationResult.Error(Exception("Ocurrió un error"))
            }
        }catch (e:Exception){
            return OperationResult.Error(e)
        }
    }

}