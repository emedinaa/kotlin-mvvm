package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MuseumViewModel(private val repository: MuseumDataSource):ViewModel() {

    private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>> = _museums

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMuseums()" on constructor. Also, if you rotate the screen, the service will not be called.
     */
    init {
        loadMuseums()
    }

    fun refresh(){
        loadMuseums()
    }

    private fun loadMuseums(){
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            var  result:OperationResult<Museum>? =null
            withContext(Dispatchers.IO){
                result = repository.retrieveMuseums()
            }
            _isViewLoading.postValue(false)
            when(result){
                is OperationResult.Success ->{
                    ( result as? OperationResult.Success)?.let {
                        if(it.data.isNullOrEmpty()){
                            _isEmptyList.postValue(true)
                        }else{
                            _museums.value = it.data
                        }
                    }
                }
                is OperationResult.Error ->{
                    ( result as? OperationResult.Error)?.let {
                        _onMessageError.postValue(it.exception)
                    }
                }
            }
        }
    }
}