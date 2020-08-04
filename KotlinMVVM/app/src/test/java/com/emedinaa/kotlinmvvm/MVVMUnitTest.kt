package com.emedinaa.kotlinmvvm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.exception.EmptyListException
import com.emedinaa.kotlinmvvm.exception.ServiceException
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.*

/**
 * @author : Eduardo Medina
 */
class MVVMUnitTest {

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel:MuseumViewModel

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderMuseumsObserver:Observer<List<Museum>>

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
        `when`(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test(expected = EmptyListException::class)
    fun `retrieve exception when Repository returns empty data`() {
        viewModel= MuseumViewModel(fakeEmptyMuseumRepository)

        runBlockingTest {
            val flow = fakeEmptyMuseumRepository.retrieveMuseumsFlow()
            flow.single()
        }
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns empty data`() {
        viewModel= MuseumViewModel(fakeEmptyMuseumRepository)

        runBlockingTest {
               fakeEmptyMuseumRepository.retrieveMuseumsFlow()
                val liveData = viewModel.loadMuseumsFlow()
                liveData.observeForever(onRenderMuseumsObserver)
                viewModel.isViewLoading.observeForever(isViewLoadingObserver)
                viewModel.isEmptyList.observeForever(emptyListObserver)

                Assert.assertNotNull(viewModel.isViewLoading.value)
                Assert.assertTrue(viewModel.isEmptyList.value==true)

                verify(isViewLoadingObserver).onChanged(false)
                verify(emptyListObserver).onChanged(true)
        }
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`() {
        viewModel= MuseumViewModel(fakeMuseumRepository)

        with(viewModel) {
            loadMuseumsFlow()
            isViewLoading.observeForever(isViewLoadingObserver)
        }

        runBlockingTest {
            fakeMuseumRepository.retrieveMuseumsFlow()
            val liveData = viewModel.loadMuseumsFlow()
            liveData.observeForever(onRenderMuseumsObserver)
            viewModel.isViewLoading.observeForever(isViewLoadingObserver)
            viewModel.isEmptyList.observeForever(emptyListObserver)

            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(liveData.value?.size ==3)

            verify(isViewLoadingObserver).onChanged(false)
            verify(onRenderMuseumsObserver).onChanged(museumList)
        }
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`() {
        viewModel= MuseumViewModel(fakeErrorMuseumRepository)
        with(viewModel) {
            loadMuseumsFlow()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }

        runBlockingTest {
            try {
                val flow = fakeErrorMuseumRepository.retrieveMuseumsFlow()
                flow.single()
            }catch (e:ServiceException) {
                println("e $e")
            }
        }
    }

    @Test(expected = ServiceException::class)
    fun `retrieve exception when Repository returns an error`() {
        viewModel= MuseumViewModel(fakeErrorMuseumRepository)

        runBlockingTest {
            val flow = fakeErrorMuseumRepository.retrieveMuseumsFlow()
            flow.single()
        }
    }

    private fun setupObservers(){
        isViewLoadingObserver= mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver= mock(Observer::class.java) as Observer<Any>
        emptyListObserver= mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver= mock(Observer::class.java)as Observer<List<Museum>>
    }

    private fun mockData(){
        val mockList:MutableList<Museum>  = mutableListOf()
        mockList.add(Museum(0,"Museo Nacional de Arqueología, Antropología e Historia del Perú",""))
        mockList.add(Museum(1,"Museo de Sitio Pachacamac",""))
        mockList.add(Museum(2,"Casa Museo José Carlos Mariátegui",""))

        museumList= mockList.toList()
    }
}