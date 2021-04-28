package com.aemiralfath.moviecatalogue.data

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.utils.DataDummy
import com.aemiralfath.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MovieRepositoryTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val movieRepository = FakeMainRepository(remote)

    private val dummyMovies = DataDummy.loadMovieRemote(context)
    private val movie = DetailMovieResponse(
        overview = dummyMovies[0].overview,
        originalLanguage = dummyMovies[0].originalLanguage,
        title = dummyMovies[0].title,
        posterPath = dummyMovies[0].posterPath,
        releaseDate = dummyMovies[0].releaseDate,
        popularity = dummyMovies[0].popularity,
        voteAverage = dummyMovies[0].voteAverage,
        id = dummyMovies[0].id,
        adult = dummyMovies[0].adult,
        voteCount = dummyMovies[0].voteCount,
    )

    private val dummyTv = DataDummy.loadTvRemote(context)
    private val tv = DetailTvResponse(
        firstAirDate = dummyTv[0].firstAirDate,
        overview = dummyTv[0].overview,
        originalLanguage = dummyTv[0].originalLanguage,
        posterPath = dummyTv[0].posterPath,
        popularity = dummyTv[0].popularity,
        voteAverage = dummyTv[0].voteAverage,
        name = dummyTv[0].name,
        id = dummyTv[0].id,
        voteCount = dummyTv[0].voteCount,
    )

    @Test
    fun getAllMovies() {
        doAnswer {
            (it.arguments[0] as RemoteDataSource.LoadMoviesCallback)
                .onAllMoviesReceived(dummyMovies)
            null
        }.`when`(remote).getAllMovies(any())

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getAllMovies())
        verify(remote).getAllMovies(any())
        assertNotNull(movieEntities)
        assertEquals(dummyMovies.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getAllTv() {
        doAnswer {
            (it.arguments[0] as RemoteDataSource.LoadTvCallback)
                .onAllTvCallback(dummyTv)
            null
        }.`when`(remote).getAllTv(any())

        val tvEntities = LiveDataTestUtil.getValue(movieRepository.getAllTv())
        verify(remote).getAllTv(any())
        assertNotNull(tvEntities)
        assertEquals(dummyTv.size.toLong(), tvEntities.size.toLong())
    }

    @Test
    fun getMovie() {
        movie.id?.let {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                    .onDetailMovieReceived(movie)
                null
            }.`when`(remote).getMovie(eq(it), any())
        }

        val movieEntity = LiveDataTestUtil.getValue(movie.id?.let { movieRepository.getMovie(it) })
        movie.id?.let { verify(remote).getMovie(eq(it), any()) }
        assertNotNull(movieEntity)
        assertEquals(movie.id, movieEntity.id)
        assertEquals(movie.title, movieEntity.title)
        assertEquals(movie.posterPath, movieEntity.posterPath)
        assertEquals(movie.voteAverage, movieEntity.voteAverage)
        assertEquals(movie.overview, movieEntity.overview)
    }

    @Test
    fun getTv() {
        tv.id?.let {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadDetailTvCallback)
                    .onDetailTvReceived(tv)
                null
            }.`when`(remote).getTv(eq(it), any())
        }

        val tvEntity = LiveDataTestUtil.getValue(tv.id?.let { movieRepository.getTv(it) })
        tv.id?.let { verify(remote).getTv(eq(it), any()) }
        assertNotNull(tvEntity)
        assertEquals(tv.id, tvEntity.id)
        assertEquals(tv.name, tvEntity.name)
        assertEquals(tv.posterPath, tvEntity.posterPath)
        assertEquals(tv.voteAverage, tvEntity.voteAverage)
        assertEquals(tv.overview, tvEntity.overview)
    }

}