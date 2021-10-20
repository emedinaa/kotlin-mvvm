package com.emedinaa.kotlinmvvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author : Eduardo Medina
 * @since : 11/17/18
 * @see : https://developer.android.com/index.html
 */

@Parcelize
data class Museum(val id: Int, val name: String, val photo: String) : Parcelable