package com.emedinaa.kotlinmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MuseumDao {
    @Query("SELECT * from tb_museum")
    fun museums(): LiveData<List<MuseumDTO>>

    @Insert(onConflict = REPLACE)
    suspend fun add(museum: List<MuseumDTO>)

    @Query("DELETE from tb_museum")
    suspend fun deleteAll()
}