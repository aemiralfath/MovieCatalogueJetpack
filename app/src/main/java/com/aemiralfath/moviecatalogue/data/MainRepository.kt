package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource.*
import com.aemiralfath.moviecatalogue.data.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.data.remote.response.ItemMovieResponse
import com.aemiralfath.moviecatalogue.data.remote.response.ItemTvResponse
import com.aemiralfath.moviecatalogue.utils.Resource

class MainRepository(private val remoteDataSource: RemoteDataSource) :
    MainDataSource {

    override fun getAllMovies(): LiveData<Resource<List<MovieEntity>>> {
        val movieResult = MutableLiveData<Resource<List<MovieEntity>>>()

        remoteDataSource.getAllMovies(object : LoadMoviesCallback {
            override fun onAllMoviesReceived(movieResponse: Resource<List<ItemMovieResponse?>?>) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponse.data != null) {
                    for (response in movieResponse.data) {
                        val movie = MovieEntity(
                            response?.overview,
                            response?.originalLanguage,
                            response?.title,
                            response?.posterPath,
                            response?.releaseDate,
                            response?.popularity,
                            response?.voteAverage,
                            response?.id,
                            response?.adult,
                            response?.voteCount,
                        )
                        movieList.add(movie)
                    }
                }

                movieResult.postValue(
                    Resource(
                        movieResponse.status,
                        movieList,
                        movieResponse.message
                    )
                )
            }
        })

        return movieResult
    }

    override fun getAllTv(): LiveData<Resource<List<TvEntity>>> {
        val tvResult = MutableLiveData<Resource<List<TvEntity>>>()

        remoteDataSource.getAllTv(object : LoadTvCallback {
            override fun onAllTvCallback(tvResponse: Resource<List<ItemTvResponse?>?>) {
                val tvList = ArrayList<TvEntity>()
                if (tvResponse.data != null) {
                    for (response in tvResponse.data) {
                        val tv = TvEntity(
                            response?.firstAirDate,
                            response?.overview,
                            response?.originalLanguage,
                            response?.posterPath,
                            response?.popularity,
                            response?.voteAverage,
                            response?.name,
                            response?.id,
                            response?.voteCount,
                        )
                        tvList.add(tv)
                    }
                }

                tvResult.postValue(
                    Resource(
                        tvResponse.status,
                        tvList,
                        tvResponse.message
                    )
                )
            }
        })

        return tvResult
    }

    override fun getMovie(id: Int): LiveData<Resource<MovieEntity>> {
        val movieResult = MutableLiveData<Resource<MovieEntity>>()

        remoteDataSource.getMovie(id, object : LoadDetailMovieCallback {
            override fun onDetailMovieReceived(detailMovieResponse: Resource<DetailMovieResponse>) {
                var movieEntity = MovieEntity()
                if (detailMovieResponse.data != null) {
                    movieEntity = MovieEntity(
                        detailMovieResponse.data.overview,
                        detailMovieResponse.data.originalLanguage,
                        detailMovieResponse.data.title,
                        detailMovieResponse.data.posterPath,
                        detailMovieResponse.data.releaseDate,
                        detailMovieResponse.data.popularity,
                        detailMovieResponse.data.voteAverage,
                        detailMovieResponse.data.id,
                        detailMovieResponse.data.adult,
                        detailMovieResponse.data.voteCount,
                    )
                }

                movieResult.postValue(
                    Resource(
                        detailMovieResponse.status,
                        movieEntity,
                        detailMovieResponse.message
                    )
                )
            }
        })

        return movieResult
    }

    override fun getTv(id: Int): LiveData<Resource<TvEntity>> {
        val tvResult = MutableLiveData<Resource<TvEntity>>()

        remoteDataSource.getTv(id, object : LoadDetailTvCallback {
            override fun onDetailTvReceived(detailTvResponse: Resource<DetailTvResponse>) {
                var tvEntity = TvEntity()
                if (detailTvResponse.data != null) {
                    tvEntity = TvEntity(
                        detailTvResponse.data.firstAirDate,
                        detailTvResponse.data.overview,
                        detailTvResponse.data.originalLanguage,
                        detailTvResponse.data.posterPath,
                        detailTvResponse.data.popularity,
                        detailTvResponse.data.voteAverage,
                        detailTvResponse.data.name,
                        detailTvResponse.data.id,
                        detailTvResponse.data.voteCount,
                    )
                }

                tvResult.postValue(
                    Resource(
                        detailTvResponse.status,
                        tvEntity,
                        detailTvResponse.message
                    )
                )
            }
        })

        return tvResult
    }
}