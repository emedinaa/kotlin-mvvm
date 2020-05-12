package com.emedinaa.kotlinmvvm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "tb_museum")
data class MuseumDTO(@PrimaryKey val id:Int,
                     @ColumnInfo(name = "name") val name:String,
                     @ColumnInfo(name = "photo") val  photo:String)