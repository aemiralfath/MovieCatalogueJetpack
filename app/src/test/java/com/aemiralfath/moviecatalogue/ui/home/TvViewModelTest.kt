package com.aemiralfath.moviecatalogue.ui.home

import android.os.Build
import com.aemiralfath.moviecatalogue.ui.movie.MovieViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class TvViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @Test
    fun setMovie() {
        runBlocking {
            viewModel.setMovie()
            viewModel.getMovie().observeForever {
                assertNotNull(it)
            }
            return@runBlocking
        }
    }

    @Test
    fun setTv() {
        runBlocking {
            viewModel.setTv()
            viewModel.getTv().observeForever {
                assertNotNull(it)
            }
            return@runBlocking
        }
    }

    @Test
    fun getMovie() {
        runBlocking {
            viewModel.setMovie()
            viewModel.getMovie().observeForever {
                assertEquals(20, it.results?.size)
            }
            return@runBlocking
        }
    }

    @Test
    fun getTv() {
        runBlocking {
            viewModel.setTv()
            viewModel.getTv().observeForever {
                assertEquals(20, it.results?.size)
            }
            return@runBlocking
        }
    }
}