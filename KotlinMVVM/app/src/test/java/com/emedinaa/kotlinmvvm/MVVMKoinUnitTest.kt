package com.emedinaa.kotlinmvvm

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.data.OperationCallback
import com.emedinaa.kotlinmvvm.di.appModule
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumDataSource
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import org.junit.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.*

class MVVMKoinUnitTest:KoinTest {

    @Mock private lateinit var repository: MuseumDataSource
    @Mock private lateinit var context: Application
    @Captor private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback>

    private val museumViewModel:MuseumViewModel by inject()

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderMuseumsObserver:Observer<List<Museum>>

    private lateinit var museumEmptyList:List<Museum>
    private lateinit var museumList:List<Museum>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin {
            printLogger()
            loadKoinModules(module{
                single<MuseumDataSource>{ repository }
                viewModel{
                    MuseumViewModel(get())
                }
            })
        }

        //setup
        MockitoAnnotations.initMocks(this)
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)

        mockData()
        setupObservers()
    }

    @Test
    fun `empty museums list with Repository and ViewModel`(){
        with(museumViewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumEmptyList)
        Assert.assertNotNull(museumViewModel.isViewLoading.value)
        Assert.assertTrue(museumViewModel.isEmptyList.value==true)
        Assert.assertTrue(museumViewModel.museums.value?.size==0)
    }

    @Test
    fun `museum list successful with Repository and ViewModel`(){
        with(museumViewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumList)

        Assert.assertNotNull(museumViewModel.isViewLoading.value)
        Assert.assertTrue(museumViewModel.museums.value?.size==3)
    }

    @Test
    fun `museum list failure with Repository and ViewModel`(){
        with(museumViewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        Mockito.verify(repository, Mockito.times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Ocurrió un error")
        Assert.assertNotNull(museumViewModel.isViewLoading.value)
        Assert.assertNotNull(museumViewModel.onMessageError.value)
    }



    private fun setupObservers(){
        isViewLoadingObserver= Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver= Mockito.mock(Observer::class.java) as Observer<Any>
        emptyListObserver= Mockito.mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver= Mockito.mock(Observer::class.java) as Observer<List<Museum>>
    }

    private fun mockData(){
        museumEmptyList= emptyList()
        val mockList:MutableList<Museum>  = mutableListOf()
        mockList.add(Museum(0,"Museo Nacional de Arqueología, Antropología e Historia del Perú",""))
        mockList.add(Museum(1,"Museo de Sitio Pachacamac",""))
        mockList.add(Museum(2,"Casa Museo José Carlos Mariátegui",""))

        museumList= mockList.toList()
    }

    @After
    fun after() {
        stopKoin()
    }
}