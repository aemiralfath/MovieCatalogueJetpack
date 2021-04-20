package com.aemiralfath.moviecatalogue.ui.movie

import android.os.Build
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
class MovieViewModelTest {

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
    fun getMovie() {
        runBlocking {
            viewModel.setMovie()
            viewModel.getMovie().observeForever {
                assertEquals(20, it.results?.size)
            }
            return@runBlocking
        }
    }
}