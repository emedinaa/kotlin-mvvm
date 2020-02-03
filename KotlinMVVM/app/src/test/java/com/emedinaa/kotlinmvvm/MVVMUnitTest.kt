package com.emedinaa.kotlinmvvm

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.data.OperationResult
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.*


class MVVMUnitTest {

    @Mock
    private lateinit var context: Application


    private lateinit var viewModel:MuseumViewModel

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderMuseumsObserver:Observer<List<Museum>>

    private lateinit var museumEmptyList:List<Museum>
    private lateinit var museumList:List<Museum>

    private val fakeMuseumRepository = FakeMuseumRepository()
    private val fakeEmptyMuseumRepository = FakeEmptyMuseumRepository()
    private val fakeErrorMuseumRepository = FakeErrorMuseumRepository()

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `retrieve museums with ViewModel and Repository returns empty data`() {
        viewModel= MuseumViewModel(fakeEmptyMuseumRepository)

        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeEmptyMuseumRepository.retrieveMuseums()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(viewModel.isEmptyList.value==true)
            Assert.assertTrue(viewModel.museums.value?.size==0)
        }
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`() {
        viewModel= MuseumViewModel(fakeMuseumRepository)

        with(viewModel) {
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeMuseumRepository.retrieveMuseums()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(viewModel.museums.value?.size==3)
        }
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`() {
        viewModel= MuseumViewModel(fakeErrorMuseumRepository)
        with(viewModel) {
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeErrorMuseumRepository.retrieveMuseums()
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertNotNull(viewModel.onMessageError.value)
        }
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