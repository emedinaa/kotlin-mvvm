package com.emedinaa.kotlinmvvm.viewmodel

import com.emedinaa.kotlinmvvm.model.Museum

sealed class MuseumUIState {
    data class Success(val museums: List<Museum>) : MuseumUIState()
    data class Error(val exception: Any?) : MuseumUIState()
    data class ViewLoading(val visible: Boolean = false) : MuseumUIState()
    object Empty : MuseumUIState()
}
