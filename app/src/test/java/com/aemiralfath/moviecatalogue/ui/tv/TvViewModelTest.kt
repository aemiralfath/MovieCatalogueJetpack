package com.aemiralfath.moviecatalogue.ui.tv

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.SortUtils
import com.aemiralfath.moviecatalogue.vo.Resource
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
    private lateinit var query: String
    private lateinit var sort: String

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var movieRepository: MainRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var observerFavorite: Observer<PagedList<TvEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieRepository)
        context = ApplicationProvider.getApplicationContext()
        query = ""
        sort = SortUtils.NEWEST
    }

    @Test
    fun getTv() {
        val dummyTv = Resource.success(pagedList)
        `when`(dummyTv.data?.size).thenReturn(20)

        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv(sort, query)).thenReturn(tv)
        val tvEntities = viewModel.getTv(sort, query).value?.data
        verify(movieRepository).getAllTv(sort, query)

        assertNotNull(tvEntities)
        assertEquals(20, tvEntities?.size)

        viewModel.getTv(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }

    @Test
    fun getFavoriteTv() {
        val dummyFavorite = pagedList
        `when`(dummyFavorite.size).thenReturn(5)

        val tv = MutableLiveData<PagedList<TvEntity>>()
        tv.value = dummyFavorite

        `when`(movieRepository.getFavoriteTv()).thenReturn(tv)
        val tvEntities = viewModel.getFavoriteTv().value
        verify(movieRepository).getFavoriteTv()

        assertNotNull(tvEntities)
        assertEquals(5, tvEntities?.size)

        viewModel.getFavoriteTv().observeForever(observerFavorite)
        verify(observerFavorite).onChanged(dummyFavorite)
    }

    @Test
    fun getTvEmpty() {
        val dummyTv = Resource.success(pagedList)
        `when`(dummyTv.data?.size).thenReturn(0)

        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv(sort, query)).thenReturn(tv)
        val tvEntities = viewModel.getTv(sort, query).value?.data
        verify(movieRepository).getAllTv(sort, query)
        assertNotNull(tvEntities)
        assertEquals(0, tvEntities?.size)

        viewModel.getTv(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }

    @Test
    fun getTvFailed() {
        val dummyTv = Resource.error("fail", pagedList)

        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv(sort, query)).thenReturn(tv)
        val tvEntities = viewModel.getTv(sort, query).value

        assertNotNull(tvEntities)
        assertEquals("fail", tvEntities?.message)

        viewModel.getTv(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}