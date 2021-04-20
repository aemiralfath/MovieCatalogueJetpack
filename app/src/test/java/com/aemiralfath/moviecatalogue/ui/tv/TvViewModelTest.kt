package com.aemiralfath.moviecatalogue.ui.tv

import android.os.Build
import kotlinx.coroutines.runBlocking
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
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @Before
    fun setUp() {
        viewModel = TvViewModel()
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