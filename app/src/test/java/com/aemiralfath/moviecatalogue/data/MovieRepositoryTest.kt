package com.aemiralfath.moviecatalogue.data

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.source.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.utils.DataDummy
import com.aemiralfath.moviecatalogue.utils.LiveDataTestUtil
import com.aemiralfath.moviecatalogue.vo.Resource
import com.aemiralfath.moviecatalogue.vo.Status
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

    private val dummyMovies =
        Resource(Status.SUCCESS, DataDummy.loadMovieRemote(context), "success")

    private val movie = Resource(
        Status.SUCCESS,
        DetailMovieResponse(
            overview = dummyMovies.data?.get(0)?.overview,
            originalLanguage = dummyMovies.data?.get(0)?.originalLanguage,
            title = dummyMovies.data?.get(0)?.title,
            posterPath = dummyMovies.data?.get(0)?.posterPath,
            releaseDate = dummyMovies.data?.get(0)?.releaseDate,
            popularity = dummyMovies.data?.get(0)?.popularity,
            voteAverage = dummyMovies.data?.get(0)?.voteAverage,
            id = dummyMovies.data?.get(0)?.id,
            adult = dummyMovies.data?.get(0)?.adult,
            voteCount = dummyMovies.data?.get(0)?.voteCount,
        ),
        "success"
    )


    private val dummyTv = Resource(Status.SUCCESS, DataDummy.loadTvRemote(context), "success")
    private val tv = Resource(
        Status.SUCCESS,
        DetailTvResponse(
            firstAirDate = dummyTv.data?.get(0)?.firstAirDate,
            overview = dummyTv.data?.get(0)?.overview,
            originalLanguage = dummyTv.data?.get(0)?.originalLanguage,
            posterPath = dummyTv.data?.get(0)?.posterPath,
            popularity = dummyTv.data?.get(0)?.popularity,
            voteAverage = dummyTv.data?.get(0)?.voteAverage,
            name = dummyTv.data?.get(0)?.name,
            id = dummyTv.data?.get(0)?.id,
            voteCount = dummyTv.data?.get(0)?.voteCount,
        ),
        "success"
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
        assertEquals(dummyMovies.data?.size?.toLong(), movieEntities.data?.size?.toLong())
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
        assertEquals(dummyTv.data?.size?.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        movie.data?.id?.let {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                    .onDetailMovieReceived(movie)
                null
            }.`when`(remote).getMovie(eq(it), any())
        }

        val movieEntity =
            LiveDataTestUtil.getValue(movie.data?.id?.let { movieRepository.getMovie(it) })
        movie.data?.id?.let { verify(remote).getMovie(eq(it), any()) }
        assertNotNull(movieEntity)
        assertEquals(movie.data?.id, movieEntity.data?.id)
        assertEquals(movie.data?.title, movieEntity.data?.title)
        assertEquals(movie.data?.posterPath, movieEntity.data?.posterPath)
        assertEquals(movie.data?.voteAverage, movieEntity.data?.voteAverage)
        assertEquals(movie.data?.overview, movieEntity.data?.overview)
    }

    @Test
    fun getTv() {
        tv.data?.id?.let {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadDetailTvCallback)
                    .onDetailTvReceived(tv)
                null
            }.`when`(remote).getTv(eq(it), any())
        }

        val tvEntity = LiveDataTestUtil.getValue(tv.data?.id?.let { movieRepository.getTv(it) })
        tv.data?.id?.let { verify(remote).getTv(eq(it), any()) }
        assertNotNull(tvEntity)
        assertEquals(tv.data?.id, tvEntity.data?.id)
        assertEquals(tv.data?.name, tvEntity.data?.name)
        assertEquals(tv.data?.posterPath, tvEntity.data?.posterPath)
        assertEquals(tv.data?.voteAverage, tvEntity.data?.voteAverage)
        assertEquals(tv.data?.overview, tvEntity.data?.overview)
    }

}