package com.aemiralfath.moviecatalogue.ui.detail.movie

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
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
class DetailMovieViewModelTest {

    private lateinit var context: Context
    private lateinit var dummyMovie: MovieEntity
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var movieRepository: MainRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = DetailMovieViewModel(movieRepository)
        dummyMovie = DataDummy.loadMovie(context)[0]
    }

    @Test
    fun getMovie() {
        val response = Resource(Status.SUCCESS, dummyMovie, "success")

        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.postValue(response)

        dummyMovie.id.let {
            `when`(movieRepository.getMovie(it)).thenReturn(movie)
            val movieEntity = viewModel.getMovie(it).value
            verify(movieRepository).getMovie(it)

            assertNotNull(movieEntity)
            assertEquals(dummyMovie.id, movieEntity?.data?.id)
            assertEquals(dummyMovie.title, movieEntity?.data?.title)
            assertEquals(dummyMovie.releaseDate, movieEntity?.data?.releaseDate)
            assertEquals(dummyMovie.voteAverage, movieEntity?.data?.voteAverage)
            assertEquals(dummyMovie.originalLanguage, movieEntity?.data?.originalLanguage)
            assertEquals(dummyMovie.popularity, movieEntity?.data?.popularity)
            assertEquals(dummyMovie.voteCount, movieEntity?.data?.voteCount)

            viewModel.getMovie(it).observeForever(observer)
            verify(observer).onChanged(response)
        }
    }

    @Test
    fun setFavorite() {
        val response = Resource(Status.SUCCESS, dummyMovie, "success")
        val movie = MutableLiveData<Resource<MovieEntity>>()

        movie.postValue(response)
        movieRepository.setMovieFavorite(dummyMovie, true)

        dummyMovie.id.let {
            `when`(movieRepository.getMovie(it)).thenReturn(movie)

            val movieEntity = viewModel.getMovie(it).value
            verify(movieRepository).getMovie(it)

            dummyMovie.favorite = true
            assertNotNull(movieEntity)
            assertEquals(dummyMovie.id, movieEntity?.data?.id)
            assertEquals(dummyMovie.favorite, movieEntity?.data?.favorite)

            viewModel.getMovie(it).observeForever(observer)
            verify(observer).onChanged(response)
        }
    }
}