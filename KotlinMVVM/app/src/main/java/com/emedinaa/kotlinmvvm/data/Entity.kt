package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.model.Museum

/**
 * @author Eduardo Medina
 */
data class MuseumResponse(val status: Int?, val msg: String?, val data: List<Museum>?) {
    fun isSuccess(): Boolean = (status == 200)
}