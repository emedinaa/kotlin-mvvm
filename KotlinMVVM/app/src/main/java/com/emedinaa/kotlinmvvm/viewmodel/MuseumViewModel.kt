package com.emedinaa.kotlinmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emedinaa.kotlinmvvm.data.OperationCallback
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository

class MuseumViewModel:ViewModel() {

    private val repository= MuseumRepository()

    private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>>
    get() = _museums

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean>
    get() = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any>
    get() = _onMessageError


    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMuseums()" on constructor. Also, if you rotate the screen, the service will not be called.
     */
    init {
        loadMuseums()
    }

    fun loadMuseums(){
        _isViewLoading.value=true
        repository.retrieveMuseums(object:OperationCallback{
            override fun onError(obj: Any?) {
                _isViewLoading.value=false
                _onMessageError.value= obj
            }

            override fun onSuccess(obj: Any?) {
                if(obj is List<*>){
                    _isViewLoading.value=false
                    _museums.value= obj as List<Museum>
                }
            }
        })

    }
}