package com.emedinaa.kotlinmvvm

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.data.OperationCallback
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.*

class MVVMUnitTest {

    @Mock
    private lateinit var repository: MuseumDataSource
    @Mock private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<Museum>>

    private lateinit var viewModel:MuseumViewModel

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderMuseumsObserver:Observer<List<Museum>>

    private lateinit var museumEmptyList:List<Museum>
    private lateinit var museumList:List<Museum>

    /**
     //https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
     //Method getMainLooper in android.os.Looper not mocked

     java.lang.IllegalStateException: operationCallbackCaptor.capture() must not be null
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)

        viewModel= MuseumViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns empty data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            //onMessageError.observeForever(onMessageErrorObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        //Assert.assertNotNull(viewModel.onMessageError.value) //java.lang.AssertionError
        //Assert.assertNotNull(viewModel.isEmptyList.value)
        Assert.assertTrue(viewModel.isEmptyList.value==true)
        Assert.assertTrue(viewModel.museums.value?.size==0)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.museums.value?.size==3)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers(){
        isViewLoadingObserver= mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver= mock(Observer::class.java) as Observer<Any>
        emptyListObserver= mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver= mock(Observer::class.java)as Observer<List<Museum>>
    }

    private fun mockData(){
        museumEmptyList= emptyList()
        val mockList:MutableList<Museum>  = mutableListOf()
        mockList.add(Museum(0,"Museo Nacional de Arqueología, Antropología e Historia del Perú",""))
        mockList.add(Museum(1,"Museo de Sitio Pachacamac",""))
        mockList.add(Museum(2,"Casa Museo José Carlos Mariátegui",""))

        museumList= mockList.toList()
    }
}