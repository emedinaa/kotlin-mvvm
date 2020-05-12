package com.emedinaa.kotlinmvvm.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.emedinaa.kotlinmvvm.data.db.DbDataSource
import com.emedinaa.kotlinmvvm.data.db.MuseumDTO
import com.emedinaa.kotlinmvvm.data.db.MuseumDao
import com.emedinaa.kotlinmvvm.data.db.MuseumDataBase

class MuseumDbDataSource(context:Context):DbDataSource {
    private lateinit var museumDao:MuseumDao
    init {
        val db = MuseumDataBase.getInstance(context)
        db?.let {
            museumDao = it.museumDao()
        }
    }
    override fun museums(): LiveData<List<MuseumDTO>> {
        return museumDao.museums()
    }

    override suspend fun addMuseums(museums: List<MuseumDTO>) {
        return museumDao.add(museums)
    }

    override suspend fun deleteMuseums() {
        museumDao.deleteAll()
    }
}