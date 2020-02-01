package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationResult

class MuseumRepository:MuseumDataSource {

    override suspend fun retrieveMuseums():OperationResult<Museum> {
        val response = ApiClient.build()?.museums()

        try {
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

        /*call?.enqueue(object :Callback<MuseumResponse>{
            override fun onFailure(call: Call<MuseumResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<MuseumResponse>, response: Response<MuseumResponse>) {
                response?.body()?.let {
                    if(response.isSuccessful && (it.isSuccess())){
                        Log.v(TAG, "data ${it.data}")
                        callback.onSuccess(it.data)
                    }else{
                        callback.onError(it.msg)
                    }
                }
            }
        })*/
    }

}