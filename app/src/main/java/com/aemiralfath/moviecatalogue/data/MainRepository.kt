package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aemiralfath.moviecatalogue.data.source.local.LocalDataSource
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.source.remote.ApiResponse
import com.aemiralfath.moviecatalogue.data.source.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.MovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.TvResponse
import com.aemiralfath.moviecatalogue.utils.AppExecutors
import com.aemiralfath.moviecatalogue.utils.SearchUtils
import com.aemiralfath.moviecatalogue.utils.SortUtils
import com.aemiralfath.moviecatalogue.vo.Resource

class MainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MainDataSource {

    override fun getAllMovies(
        sort: String,
        query: String
    ): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()

                return if (query.isBlank()) {
                    val sortType = SortUtils.getSortedQuery(sort, 1, MovieEntity.TABLE_NAME)
                    LivePagedListBuilder(
                        localDataSource.getMovieQuery(sortType),
                        config
                    ).build()
                } else {
                    val searchType = SearchUtils.getSearchQuery(query, sort, MovieEntity.TABLE_NAME)
                    LivePagedListBuilder(
                        localDataSource.getMovieQuery(searchType),
                        config
                    ).build()
                }
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                return if (query.isBlank()) {
                    remoteDataSource.getAllMovies()
                } else {
                    remoteDataSource.searchMovies(query)
                }
            }

            override fun saveCallResult(data: MovieResponse) {
                val movieList = ArrayList<MovieEntity>()
                if (data.results != null) {
                    for (response in data.results) {
                        val movie = response?.id?.let {
                            MovieEntity(
                                response.overview,
                                response.originalLanguage,
                                response.title,
                                response.posterPath,
                                response.releaseDate,
                                response.popularity,
                                response.voteAverage,
                                it,
                                response.adult,
                                response.voteCount,
                                false,
                                query.isBlank()
                            )
                        }
                        movie?.let { movieList.add(it) }
                    }
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTv(sort: String, query: String): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, TvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return if (query.isBlank()) {
                    val sortType = SortUtils.getSortedQuery(sort, 1, TvEntity.TABLE_NAME)
                    LivePagedListBuilder(
                        localDataSource.getTvQuery(sortType),
                        config
                    ).build()
                } else {
                    val searchType = SearchUtils.getSearchQuery(query, sort, TvEntity.TABLE_NAME)
                    LivePagedListBuilder(
                        localDataSource.getTvQuery(searchType),
                        config
                    ).build()
                }
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<TvResponse>> {
                return if (query.isBlank()) {
                    remoteDataSource.getAllTv()
                } else {
                    remoteDataSource.searchTv(query)
                }
            }

            override fun saveCallResult(data: TvResponse) {
                val tvList = ArrayList<TvEntity>()
                if (data.results != null) {
                    for (response in data.results) {
                        val tv = response?.id?.let {
                            TvEntity(
                                response.firstAirDate,
                                response.overview,
                                response.originalLanguage,
                                response.posterPath,
                                response.popularity,
                                response.voteAverage,
                                response.name,
                                it,
                                response.voteCount,
                                false,
                                query.isBlank()
                            )
                        }
                        tv?.let { tvList.add(it) }
                    }
                }

                localDataSource.insertTv(tvList)
            }
        }.asLiveData()
    }

    override fun getMovie(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data?.id == null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getMovie(id)

            override fun saveCallResult(data: DetailMovieResponse) {
                val movieEntity = data.id?.let {
                    MovieEntity(
                        data.overview,
                        data.originalLanguage,
                        data.title,
                        data.posterPath,
                        data.releaseDate,
                        data.popularity,
                        data.voteAverage,
                        it,
                        data.adult,
                        data.voteCount,
                    )
                }

                val movieList = ArrayList<MovieEntity>()
                movieEntity?.let { movieList.add(it) }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getTv(id: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, DetailTvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getTvById(id)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data?.id == null

            override fun createCall(): LiveData<ApiResponse<DetailTvResponse>> =
                remoteDataSource.getTv(id)

            override fun saveCallResult(data: DetailTvResponse) {
                val tvEntity = data.id?.let {
                    TvEntity(
                        data.firstAirDate,
                        data.overview,
                        data.originalLanguage,
                        data.posterPath,
                        data.popularity,
                        data.voteAverage,
                        data.name,
                        it,
                        data.voteCount,
                    )
                }

                val tvList = ArrayList<TvEntity>()
                tvEntity?.let { tvList.add(it) }
                localDataSource.insertTv(tvList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }

    override fun setTvFavorite(tv: TvEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tv, state) }
    }

}