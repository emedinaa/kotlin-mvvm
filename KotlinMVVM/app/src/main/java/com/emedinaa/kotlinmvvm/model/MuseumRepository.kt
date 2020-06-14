package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.exception.EmptyListException
import com.emedinaa.kotlinmvvm.exception.ServiceException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MuseumRepository:MuseumDataSource {

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

    override  fun retrieveMuseumsFlow(): Flow<List<Museum>> {
        return flow {
            emit(ApiClient.build()?.museums())
        }
        .map {
            val list = it?.body()?.data
            if(it?.isSuccessful==true){
                if(list.isNullOrEmpty()){
                    throw EmptyListException()
                }else{
                    list
                }
            }else{
                throw ServiceException(it?.body()?.msg)
            }
        }
        .catch { e ->
            Exception(e)
        }.flowOn(Dispatchers.IO)
    }

}