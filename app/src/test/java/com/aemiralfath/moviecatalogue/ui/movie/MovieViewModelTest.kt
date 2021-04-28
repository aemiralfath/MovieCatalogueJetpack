package com.aemiralfath.moviecatalogue.ui.movie

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var context: Context

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var movieRepository: MainRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getMovie() {
        val dummyMovies = Resource(Status.SUCCESS, DataDummy.loadMovie(context), "success")
        val movies = MutableLiveData<Resource<List<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovie().value
        verify(movieRepository).getAllMovies()

        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.data?.size)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getMovieEmpty() {
        val dummyMovies = Resource(Status.SUCCESS, listOf<MovieEntity>(), "success")
        val movies = MutableLiveData<Resource<List<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovie().value
        verify(movieRepository).getAllMovies()

        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.data?.size)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getMovieFailed() {
        val dummyMovies = Resource(Status.ERROR, null, "error")
        val movies = MutableLiveData<Resource<List<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovie().value
        verify(movieRepository).getAllMovies()

        assertNotNull(movieEntities)
        assertEquals("error", movieEntities?.message)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }


}