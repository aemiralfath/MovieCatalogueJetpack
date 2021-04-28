package com.aemiralfath.moviecatalogue.data.remote

import android.util.Log
import com.aemiralfath.moviecatalogue.BuildConfig
import com.aemiralfath.moviecatalogue.data.remote.response.*
import com.aemiralfath.moviecatalogue.data.remote.retrofit.ServiceClient
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource
import com.aemiralfath.moviecatalogue.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val client: ServiceClient) {

    private val token = BuildConfig.TMDB_API_KEY

    fun getAllMovies(callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()
        callback.onAllMoviesReceived(Resource.loading(null))
        client.buildServiceClient()
            .getListMovie(token)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()
                        ?.let { callback.onAllMoviesReceived(Resource.success(it.results)) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    callback.onAllMoviesReceived(Resource.error(t.message.toString(), null))
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getAllTv(callback: LoadTvCallback) {
        EspressoIdlingResource.increment()
        callback.onAllTvCallback(Resource.loading(null))
        client.buildServiceClient()
            .getListTvShow(token)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let { callback.onAllTvCallback(Resource.success(it.results)) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    callback.onAllTvCallback(Resource.error(t.message.toString(), null))
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getMovie(id: Int, callback: LoadDetailMovieCallback) {
        EspressoIdlingResource.increment()
        callback.onDetailMovieReceived(Resource.loading(null))
        client.buildServiceClient()
            .getMovie(id, token)
            .enqueue(object : Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let { callback.onDetailMovieReceived(Resource.success(it)) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    callback.onDetailMovieReceived(Resource.error(t.message.toString(), null))
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getTv(id: Int, callback: LoadDetailTvCallback) {
        EspressoIdlingResource.increment()
        callback.onDetailTvReceived(Resource.loading(null))
        client.buildServiceClient()
            .getTv(id, token)
            .enqueue(object : Callback<DetailTvResponse> {
                override fun onResponse(
                    call: Call<DetailTvResponse>,
                    response: Response<DetailTvResponse>
                ) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let { callback.onDetailTvReceived(Resource.success(it)) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailTvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    callback.onDetailTvReceived(Resource.error(t.message.toString(), null))
                    EspressoIdlingResource.decrement()
                }

            })
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(movieResponse: Resource<List<ItemMovieResponse?>?>)
    }

    interface LoadTvCallback {
        fun onAllTvCallback(tvResponse: Resource<List<ItemTvResponse?>?>)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(detailMovieResponse: Resource<DetailMovieResponse>)
    }

    interface LoadDetailTvCallback {
        fun onDetailTvReceived(detailTvResponse: Resource<DetailTvResponse>)
    }

}