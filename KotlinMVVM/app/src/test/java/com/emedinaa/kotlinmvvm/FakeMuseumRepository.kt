package com.emedinaa.kotlinmvvm

import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource

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

    override suspend fun retrieveMuseums(): OperationResult<Museum> {
        return OperationResult.Success(mockList)
    }
}