package com.aemiralfath.moviecatalogue.ui.detail.tv

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.DataDummy
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
    private lateinit var observer: Observer<TvEntity>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = DetailTvViewModel(movieRepository)
        dummyTv = DataDummy.loadTv(context)[0]
    }

    @Test
    fun getTv() {
        val tv = MutableLiveData<TvEntity>()
        tv.postValue(dummyTv)

        `when`(dummyTv.id?.let { movieRepository.getTv(it) }).thenReturn(tv)
        val tvEntity = dummyTv.id?.let { viewModel.getTv(it).value }
        dummyTv.id?.let { verify(movieRepository).getTv(it) }

        assertNotNull(tvEntity)
        assertEquals(dummyTv.id, tvEntity?.id)
        assertEquals(dummyTv.name, tvEntity?.name)
        assertEquals(dummyTv.firstAirDate, tvEntity?.firstAirDate)
        assertEquals(dummyTv.voteAverage, tvEntity?.voteAverage)
        assertEquals(dummyTv.originalLanguage, tvEntity?.originalLanguage)
        assertEquals(dummyTv.popularity, tvEntity?.popularity)
        assertEquals(dummyTv.voteCount, tvEntity?.voteCount)

        dummyTv.id?.let { viewModel.getTv(it).observeForever(observer) }
        verify(observer).onChanged(dummyTv)
    }
}