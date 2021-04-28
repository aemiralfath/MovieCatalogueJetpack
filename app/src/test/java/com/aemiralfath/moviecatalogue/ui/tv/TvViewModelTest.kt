package com.aemiralfath.moviecatalogue.ui.tv

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.DataDummy
import com.aemiralfath.moviecatalogue.utils.Resource
import com.aemiralfath.moviecatalogue.utils.Status
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel
    private lateinit var context: Context

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var movieRepository: MainRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<TvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieRepository)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getTv() {
        val dummyTv = Resource(Status.SUCCESS, DataDummy.loadTv(context), "success")
        val tv = MutableLiveData<Resource<List<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv()).thenReturn(tv)
        val tvEntities = viewModel.getTv().value
        verify(movieRepository).getAllTv()

        assertNotNull(tvEntities)
        assertEquals(20, tvEntities?.data?.size)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }

    @Test
    fun getTvEmpty() {
        val dummyTv = Resource(Status.SUCCESS, listOf<TvEntity>(), "success")
        val tv = MutableLiveData<Resource<List<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv()).thenReturn(tv)
        val tvEntities = viewModel.getTv().value
        verify(movieRepository).getAllTv()

        assertNotNull(tvEntities)
        assertEquals(0, tvEntities?.data?.size)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }

    @Test
    fun getTvFailed() {
        val dummyTv = Resource(Status.ERROR, null, "error")
        val tv = MutableLiveData<Resource<List<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv()).thenReturn(tv)
        val tvEntities = viewModel.getTv().value
        verify(movieRepository).getAllTv()

        assertNotNull(tvEntities)
        assertEquals("error", tvEntities?.message)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}