package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.data.remote.MuseumRemoteDataSource
import com.emedinaa.kotlinmvvm.model.MuseumDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MuseumViewModel(private val remoteRepository: MuseumRemoteDataSource,
                      private val dbRepository:MuseumDbRepository):ViewModel() {

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    val museums = dbRepository.getMuseums()

    fun retrieveMuseums(){
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            var  result:OperationResult<Museum> = withContext(Dispatchers.IO){
                remoteRepository.retrieveMuseums()
            }
            _isViewLoading.postValue(false)
            if(result is OperationResult.Success){
                withContext((Dispatchers.IO)) {
                    result.data?.let {
                        if (it.isNotEmpty()) dbRepository.sync(it)
                    }
                }
            }
        }
    }

    fun cancel(){
        viewModelScope.cancel()
    }
}