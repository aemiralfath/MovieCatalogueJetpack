package com.aemiralfath.moviecatalogue.ui.movie

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
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
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerFavorite: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
        context = ApplicationProvider.getApplicationContext()
        query = ""
        sort = SortUtils.NEWEST
    }

    @Test
    fun getMovie() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(20)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies(sort, query)).thenReturn(movies)
        val movieEntities = viewModel.getMovie(sort, query).value?.data
        verify(movieRepository).getAllMovies(sort, query)

        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getMovie(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyFavorite = pagedList
        `when`(dummyFavorite.size).thenReturn(5)

        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyFavorite

        `when`(movieRepository.getFavoriteMovie()).thenReturn(movies)
        val movieEntities = viewModel.getFavoriteMovie().value
        verify(movieRepository).getFavoriteMovie()

        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        viewModel.getFavoriteMovie().observeForever(observerFavorite)
        verify(observerFavorite).onChanged(dummyFavorite)
    }

    @Test
    fun getMovieEmpty() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(0)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies(sort, query)).thenReturn(movies)
        val movieEntities = viewModel.getMovie(sort, query).value?.data
        verify(movieRepository).getAllMovies(sort, query)
        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.size)

        viewModel.getMovie(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getMovieFailed() {
        val dummyMovies = Resource.error("fail", pagedList)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies(sort, query)).thenReturn(movies)
        val movieEntities = viewModel.getMovie(sort, query).value
        verify(movieRepository).getAllMovies(sort, query)

        assertNotNull(movieEntities)
        assertEquals("fail", movieEntities?.message)

        viewModel.getMovie(sort, query).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}