package com.aemiralfath.moviecatalogue.ui.detail.tv

import android.os.Build
import com.aemiralfath.moviecatalogue.data.entity.ItemTvEntity
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
class DetailTvViewModelTest {

    private lateinit var dummyTv: ItemTvEntity
    private lateinit var viewModel: DetailTvViewModel

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel()
        dummyTv = ItemTvEntity(
            id = 1,
            name = "Flash",
            firstAirDate = "2014-10-07",
            voteAverage = 7.7,
            overview = "Overview",
            originalLanguage = "en",
            popularity = 1274.326,
            voteCount = 7383
        )
    }

    @Test
    fun setTv() {
        viewModel.setTv(dummyTv)

        val tv = viewModel.getTv()
        assertNotNull(tv)
    }

    @Test
    fun getTv() {
        viewModel.setTv(dummyTv)

        val tv = viewModel.getTv()
        assertEquals(dummyTv.id, tv.id)
        assertEquals(dummyTv.name, tv.name)
        assertEquals(dummyTv.firstAirDate, tv.firstAirDate)
        assertEquals(dummyTv.voteAverage, tv.voteAverage)
        assertEquals(dummyTv.overview, tv.overview)
        assertEquals(dummyTv.originalLanguage, tv.originalLanguage)
        assertEquals(dummyTv.popularity, tv.popularity)
        assertEquals(dummyTv.voteCount, tv.voteCount)
    }
}