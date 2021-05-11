package com.aemiralfath.moviecatalogue.data

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.test.core.app.ApplicationProvider
import com.aemiralfath.moviecatalogue.data.source.local.LocalDataSource
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.source.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.ui.utils.PagedListUtil
import com.aemiralfath.moviecatalogue.utils.AppExecutors
import com.aemiralfath.moviecatalogue.utils.DataDummy
import com.aemiralfath.moviecatalogue.utils.LiveDataTestUtil
import com.aemiralfath.moviecatalogue.utils.SortUtils
import com.aemiralfath.moviecatalogue.vo.Resource
import com.aemiralfath.moviecatalogue.vo.Status
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("UNCHECKED_CAST")
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MovieRepositoryTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val movieRepository = FakeMainRepository(remote, local, appExecutors)

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
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>?
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies(SortUtils.NEWEST, "")

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.loadMovie(context)))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(dummyMovies.data?.size?.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTv() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>?
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieRepository.getAllTv(SortUtils.NEWEST, "")

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.loadTv(context)))
        verify(local).getAllTv()
        assertNotNull(tvEntities)
        assertEquals(dummyTv.data?.size?.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.loadMovie(context)[0]

        movie.data?.id?.let {
            `when`(local.getMovieById(it)).thenReturn(dummyEntity)

            val movieEntity =
                LiveDataTestUtil.getValue(movieRepository.getMovie(it))
            verify(local).getMovieById(it)

            assertNotNull(movieEntity)
            assertEquals(movie.data?.id, movieEntity.data?.id)
            assertEquals(movie.data?.title, movieEntity.data?.title)
            assertEquals(movie.data?.posterPath, movieEntity.data?.posterPath)
            assertEquals(movie.data?.voteAverage, movieEntity.data?.voteAverage)
            assertEquals(movie.data?.overview, movieEntity.data?.overview)
        }
    }

    @Test
    fun getTv() {
        val dummyEntity = MutableLiveData<TvEntity>()
        dummyEntity.value = DataDummy.loadTv(context)[0]

        tv.data?.id?.let {
            `when`(local.getTvById(it)).thenReturn(dummyEntity)

            val tvEntity = LiveDataTestUtil.getValue(movieRepository.getTv(it))
            verify(local).getTvById(it)

            assertNotNull(tvEntity)
            assertEquals(tv.data?.id, tvEntity.data?.id)
            assertEquals(tv.data?.name, tvEntity.data?.name)
            assertEquals(tv.data?.posterPath, tvEntity.data?.posterPath)
            assertEquals(tv.data?.voteAverage, tvEntity.data?.voteAverage)
            assertEquals(tv.data?.overview, tvEntity.data?.overview)
        }
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>?
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteMovie()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.loadMovie(context)))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities.data)
        assertEquals(dummyMovies.data?.size?.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTv() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>?
        `when`(local.getFavoriteTv()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteTv()

        val tvEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.loadTv(context)))
        verify(local).getFavoriteTv()
        assertNotNull(tvEntities.data)
        assertEquals(dummyTv.data?.size?.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun setMovieFavorite() {
        val dummyMovie = DataDummy.loadMovie(context)[0]
        doNothing().`when`(local).setFavoriteMovie(dummyMovie, !dummyMovie.favorite)

        movieRepository.setMovieFavorite(dummyMovie, !dummyMovie.favorite)
        verify(local).setFavoriteMovie(dummyMovie, !dummyMovie.favorite)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun setTvFavorite() {
        val dummyTv = DataDummy.loadTv(context)[0]
        doNothing().`when`(local).setFavoriteTv(dummyTv, !dummyTv.favorite)

        movieRepository.setTvFavorite(dummyTv, !dummyTv.favorite)
        verify(local).setFavoriteTv(dummyTv, !dummyTv.favorite)
        verifyNoMoreInteractions(local)
    }

}