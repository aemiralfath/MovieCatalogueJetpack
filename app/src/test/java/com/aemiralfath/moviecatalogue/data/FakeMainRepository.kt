package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.data.remote.response.ItemMovieResponse
import com.aemiralfath.moviecatalogue.data.remote.response.ItemTvResponse

class FakeMainRepository(private val remoteDataSource: RemoteDataSource) :
    MainDataSource {

    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val movieResult = MutableLiveData<List<MovieEntity>>()

        remoteDataSource.getAllMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onAllMoviesReceived(movieResponse: List<ItemMovieResponse?>?) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponse != null) {
                    for (response in movieResponse) {
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
                    movieResult.postValue(movieList)
                }
            }
        })

        return movieResult
    }

    override fun getAllTv(): LiveData<List<TvEntity>> {
        val tvResult = MutableLiveData<List<TvEntity>>()

        remoteDataSource.getAllTv(object : RemoteDataSource.LoadTvCallback {
            override fun onAllTvCallback(tvResponse: List<ItemTvResponse?>?) {
                val tvList = ArrayList<TvEntity>()
                if (tvResponse != null) {
                    for (response in tvResponse) {
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
                    tvResult.postValue(tvList)
                }
            }
        })

        return tvResult
    }

    override fun getMovie(id: Int): LiveData<MovieEntity> {
        val movieResult = MutableLiveData<MovieEntity>()

        remoteDataSource.getMovie(id, object : RemoteDataSource.LoadDetailMovieCallback {
            override fun onDetailMovieReceived(detailMovieResponse: DetailMovieResponse) {
                movieResult.postValue(
                    MovieEntity(
                        detailMovieResponse.overview,
                        detailMovieResponse.originalLanguage,
                        detailMovieResponse.title,
                        detailMovieResponse.posterPath,
                        detailMovieResponse.releaseDate,
                        detailMovieResponse.popularity,
                        detailMovieResponse.voteAverage,
                        detailMovieResponse.id,
                        detailMovieResponse.adult,
                        detailMovieResponse.voteCount,
                    )
                )
            }
        })

        return movieResult
    }

    override fun getTv(id: Int): LiveData<TvEntity> {
        val tvResult = MutableLiveData<TvEntity>()

        remoteDataSource.getTv(id, object : RemoteDataSource.LoadDetailTvCallback {
            override fun onDetailTvReceived(detailTvResponse: DetailTvResponse) {
                tvResult.postValue(
                    TvEntity(
                        detailTvResponse.firstAirDate,
                        detailTvResponse.overview,
                        detailTvResponse.originalLanguage,
                        detailTvResponse.posterPath,
                        detailTvResponse.popularity,
                        detailTvResponse.voteAverage,
                        detailTvResponse.name,
                        detailTvResponse.id,
                        detailTvResponse.voteCount,
                    )
                )
            }
        })

        return tvResult
    }
}