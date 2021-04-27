package com.aemiralfath.moviecatalogue.data.remote

import android.util.Log
import com.aemiralfath.moviecatalogue.BuildConfig
import com.aemiralfath.moviecatalogue.data.remote.response.*
import com.aemiralfath.moviecatalogue.data.remote.retrofit.ServiceClient
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val client: ServiceClient) {

    private val token = BuildConfig.TMDB_API_KEY

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(client: ServiceClient): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(client).apply { instance = this }
            }
    }

    fun getAllMovies(callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()
        client.buildServiceClient()
            .getListMovie(token)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let { callback.onAllMoviesReceived(it.results) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getAllTv(callback: LoadTvCallback) {
        EspressoIdlingResource.increment()
        client.buildServiceClient()
            .getListTvShow(token)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let { callback.onAllTvCallback(it.results) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getMovie(id: Int, callback: LoadDetailMovieCallback) {
        EspressoIdlingResource.increment()
        client.buildServiceClient()
            .getMovie(id, token)
            .enqueue(object : Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let { callback.onDetailMovieReceived(it) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getTv(id: Int, callback: LoadDetailTvCallback) {
        EspressoIdlingResource.increment()
        client.buildServiceClient()
            .getTv(id, token)
            .enqueue(object : Callback<DetailTvResponse> {
                override fun onResponse(
                    call: Call<DetailTvResponse>,
                    response: Response<DetailTvResponse>
                ) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let { callback.onDetailTvReceived(it) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailTvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(movieResponse: List<ItemMovieResponse?>?)
    }

    interface LoadTvCallback {
        fun onAllTvCallback(tvResponse: List<ItemTvResponse?>?)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(detailMovieResponse: DetailMovieResponse)
    }

    interface LoadDetailTvCallback {
        fun onDetailTvReceived(detailTvResponse: DetailTvResponse)
    }

}