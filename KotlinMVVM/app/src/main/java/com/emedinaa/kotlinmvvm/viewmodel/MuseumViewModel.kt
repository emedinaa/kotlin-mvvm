package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Eduardo Medina
 */
class MuseumViewModel(private val repository: MuseumRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MuseumUIState>(MuseumUIState.ViewLoading(true))
    val uiState: StateFlow<MuseumUIState> = _uiState

    fun loadMuseums() {
        viewModelScope.launch {
            var result: OperationResult<Museum> = repository.fetchMuseums()
            _uiState.value = MuseumUIState.ViewLoading()
            when (result) {
                is OperationResult.Success -> {
                    val museums = result.data ?: emptyList()
                    if (museums.isEmpty()) {
                        _uiState.value = MuseumUIState.Empty
                    } else {
                        _uiState.value = MuseumUIState.Success(museums)
                    }
                }
                is OperationResult.Error -> {
                    _uiState.value =
                        MuseumUIState.Error(result.exception ?: Exception("Ocurrió un error"))
                }
            }
        }
    }
}