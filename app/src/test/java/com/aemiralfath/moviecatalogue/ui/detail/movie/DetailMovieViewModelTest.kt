package com.aemiralfath.moviecatalogue.ui.detail.movie

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.MovieRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
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
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<MovieEntity>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = DetailMovieViewModel(movieRepository)
        dummyMovie = MovieEntity(
            id = 460465,
            title = "Mortal Kombat",
            releaseDate = "2021-04-07",
            voteAverage = 8.0,
            overview = "Overview",
            adult = false,
            originalLanguage = "en",
            popularity = 9606.216,
            voteCount = 1444
        )
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.postValue(dummyMovie)

        `when`(dummyMovie.id?.let { movieRepository.getMovie(it) }).thenReturn(movie)
        val movieEntity = dummyMovie.id?.let { viewModel.getMovie(it).value }
        dummyMovie.id?.let { verify(movieRepository).getMovie(it) }

        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity?.id)
        assertEquals(dummyMovie.title, movieEntity?.title)
        assertEquals(dummyMovie.releaseDate, movieEntity?.releaseDate)
        assertEquals(dummyMovie.voteAverage, movieEntity?.voteAverage)
        assertEquals(dummyMovie.originalLanguage, movieEntity?.originalLanguage)
        assertEquals(dummyMovie.popularity, movieEntity?.popularity)
        assertEquals(dummyMovie.voteCount, movieEntity?.voteCount)

        dummyMovie.id?.let { viewModel.getMovie(it).observeForever(observer) }
        verify(observer).onChanged(dummyMovie)
    }
}