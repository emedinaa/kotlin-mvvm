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


    init {
        loadMuseums()
    }

    fun loadMuseums(){
        repository.retrieveMuseums(object:OperationCallback{
            override fun onError(obj: Any?) {}

            override fun onSuccess(obj: Any?) {
                if(obj is List<*>){
                    Log.v("CONSOLE", "obj ${obj}")
                    _museums.value= obj as List<Museum>
                }
            }
        })

    }
}