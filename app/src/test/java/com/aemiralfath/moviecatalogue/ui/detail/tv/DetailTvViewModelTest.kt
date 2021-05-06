package com.aemiralfath.moviecatalogue.ui.detail.tv

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.DataDummy
import com.aemiralfath.moviecatalogue.vo.Resource
import com.aemiralfath.moviecatalogue.vo.Status
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
class DetailTvViewModelTest {

    private lateinit var context: Context
    private lateinit var dummyTv: TvEntity
    private lateinit var viewModel: DetailTvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var movieRepository: MainRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvEntity>>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = DetailTvViewModel(movieRepository)
        dummyTv = DataDummy.loadTv(context)[0]
    }

    @Test
    fun getTv() {
        val response = Resource(Status.SUCCESS, dummyTv, "success")

        val tv = MutableLiveData<Resource<TvEntity>>()
        tv.postValue(response)

        `when`(dummyTv.id?.let { movieRepository.getTv(it) }).thenReturn(tv)
        val tvEntity = dummyTv.id?.let { viewModel.getTv(it).value }
        dummyTv.id?.let { verify(movieRepository).getTv(it) }

        assertNotNull(tvEntity)
        assertEquals(dummyTv.id, tvEntity?.data?.id)
        assertEquals(dummyTv.name, tvEntity?.data?.name)
        assertEquals(dummyTv.firstAirDate, tvEntity?.data?.firstAirDate)
        assertEquals(dummyTv.voteAverage, tvEntity?.data?.voteAverage)
        assertEquals(dummyTv.originalLanguage, tvEntity?.data?.originalLanguage)
        assertEquals(dummyTv.popularity, tvEntity?.data?.popularity)
        assertEquals(dummyTv.voteCount, tvEntity?.data?.voteCount)

        dummyTv.id?.let { viewModel.getTv(it).observeForever(observer) }
        verify(observer).onChanged(response)
    }
}