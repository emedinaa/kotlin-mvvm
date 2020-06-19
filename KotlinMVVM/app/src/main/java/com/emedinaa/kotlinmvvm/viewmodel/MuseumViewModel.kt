package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.*
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.exception.EmptyListException
import com.emedinaa.kotlinmvvm.exception.ServiceException
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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

    fun loadMuseumsFlow():LiveData<List<Museum>>{
        return repository.retrieveMuseumsFlow()
            .onStart {
                _isViewLoading.postValue(true)
            }.catch {
                when(it){
                    is EmptyListException ->  _isEmptyList.postValue(true)
                    is ServiceException -> _onMessageError.postValue(it)
                    else -> _onMessageError.postValue(it)
                }
            }.onCompletion {
                _isViewLoading.postValue(false)
            }.asLiveData()
    }

    fun loadMuseums(){
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            var  result:OperationResult<Museum> = withContext(Dispatchers.IO){
                repository.retrieveMuseums()
            }
            _isViewLoading.postValue(false)
            when(result){
                is OperationResult.Success ->{
                    if(result.data.isNullOrEmpty()){
                            _isEmptyList.postValue(true)
                    }else{
                        _museums.value = result.data
                    }
                }
                is OperationResult.Error ->{
                    _onMessageError.postValue(result.exception)
                }
            }
        }
    }
}