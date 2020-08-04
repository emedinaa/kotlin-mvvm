package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author : Eduardo Medina
 */
class FakeMuseumRepository:MuseumDataSource {

    private val mockList:MutableList<Museum>  = mutableListOf()

    init {
        mockData()
    }

    private fun mockData(){
        mockList.add(Museum(0,"Museo Nacional de Arqueología, Antropología e Historia del Perú",""))
        mockList.add(Museum(1,"Museo de Sitio Pachacamac",""))
        mockList.add(Museum(2,"Casa Museo José Carlos Mariátegui",""))
    }

    override fun retrieveMuseumsFlow(): Flow<List<Museum>> {
        return flow {
            emit(mockList)
        }
    }
}