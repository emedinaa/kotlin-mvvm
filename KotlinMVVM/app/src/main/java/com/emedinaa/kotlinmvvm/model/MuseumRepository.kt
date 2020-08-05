package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.exception.EmptyListException
import com.emedinaa.kotlinmvvm.exception.ServiceException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @author : Eduardo Medina
 */
class MuseumRepository:MuseumDataSource {

    override  fun retrieveMuseumsFlow(): Flow<List<Museum>> {
        return flow {
            emit(ApiClient.build()?.museums())
        }
        .map {
            val list = it?.body()?.data
            if(it?.isSuccessful==true){
                if(list.isNullOrEmpty()) throw EmptyListException() else list
            }else{
                throw ServiceException(it?.body()?.msg)
            }
        }
        .catch { e ->
            Exception(e)
        }.flowOn(Dispatchers.IO)
    }

}