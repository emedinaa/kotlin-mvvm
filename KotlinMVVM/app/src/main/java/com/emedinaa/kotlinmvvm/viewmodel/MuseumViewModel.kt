package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emedinaa.kotlinmvvm.message.OperationCallback
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository

/**
 * @author Eduardo Medina
 */
class MuseumViewModel(private val repository: MuseumRepository) : ViewModel() {

    private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>> = _museums

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadMuseums() {
        _isViewLoading.value = true
        repository.retrieveMuseums(object : OperationCallback {
            override fun onError(obj: Exception) {
                _isViewLoading.value = false
                _onMessageError.value = obj.message
            }

            override fun onSuccess(obj: List<Museum>) {
                _isViewLoading.value = false
                if (obj.isEmpty()) {
                    _isEmptyList.value = true
                } else {
                    _museums.value = obj
                }
            }
        })
    }

}