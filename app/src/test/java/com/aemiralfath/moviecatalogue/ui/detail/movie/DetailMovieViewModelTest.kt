package com.aemiralfath.moviecatalogue.ui.detail.movie

import android.os.Build
import com.aemiralfath.moviecatalogue.data.entity.ItemMovieEntity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class DetailMovieViewModelTest {

    private lateinit var dummyMovie: ItemMovieEntity
    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
        dummyMovie = ItemMovieEntity(
            id = 1,
            title = "twist",
            releaseDate = "2021-01-22",
            voteAverage = 7.0,
            overview = "Overview",
            adult = false,
            originalLanguage = "en",
            popularity = 1179.035,
            voteCount = 39
        )
    }

    @Test
    fun setMovie() {
        viewModel.setMovie(dummyMovie)

        val movie = viewModel.getMovie()
        assertNotNull(movie)
    }

    @Test
    fun getMovie() {
        viewModel.setMovie(dummyMovie)

        val movie = viewModel.getMovie()
        assertEquals(dummyMovie.id, movie.id)
        assertEquals(dummyMovie.title, movie.title)
        assertEquals(dummyMovie.releaseDate, movie.releaseDate)
        assertEquals(dummyMovie.voteAverage, movie.voteAverage)
        assertEquals(dummyMovie.overview, movie.overview)
        assertEquals(dummyMovie.originalLanguage, movie.originalLanguage)
        assertEquals(dummyMovie.popularity, movie.popularity)
        assertEquals(dummyMovie.voteCount, movie.voteCount)
        assertEquals(dummyMovie.voteCount, movie.voteCount)
    }
}