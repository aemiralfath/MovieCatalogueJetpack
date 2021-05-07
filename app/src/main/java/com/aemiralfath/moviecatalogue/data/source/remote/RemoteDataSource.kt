package com.aemiralfath.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aemiralfath.moviecatalogue.BuildConfig
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.MovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.TvResponse
import com.aemiralfath.moviecatalogue.data.source.remote.retrofit.ServiceClient
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val client: ServiceClient) {

    private val token = BuildConfig.TMDB_API_KEY

    fun getAllMovies(): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        client.buildServiceClient()
            .getListMovie(token)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let {
                        resultMovie.value = ApiResponse.success(it)

                        if (it.totalResults == 0) {
                            resultMovie.value = ApiResponse.empty("Empty List", it)
                        }
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    resultMovie.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultMovie
    }

    fun getAllTv(): LiveData<ApiResponse<TvResponse>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<TvResponse>>()
        client.buildServiceClient()
            .getListTvShow(token)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let {
                        resultTv.value = ApiResponse.success(it)

                        if (it.totalResults == 0) {
                            resultTv.value = ApiResponse.empty("Empty List", it)
                        }
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    resultTv.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultTv
    }

    fun searchMovies(query: String): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        client.buildServiceClient()
            .searchMovie(token, query)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let {
                        resultMovie.value = ApiResponse.success(it)

                        if (it.totalResults == 0) {
                            resultMovie.value = ApiResponse.empty("Empty List", it)
                        }
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    resultMovie.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultMovie
    }

    fun searchTv(query: String): LiveData<ApiResponse<TvResponse>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<TvResponse>>()
        client.buildServiceClient()
            .searchTv(token, query)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let {
                        resultTv.value = ApiResponse.success(it)

                        if (it.totalResults == 0) {
                            resultTv.value = ApiResponse.empty("Empty List", it)
                        }
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    resultTv.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultTv
    }

    fun getMovie(id: Int): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        client.buildServiceClient()
            .getMovie(id, token)
            .enqueue(object : Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    Log.d("MovieGet", response.body().toString())
                    response.body()?.let {
                        resultDetailMovie.value = ApiResponse.success(it)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    resultDetailMovie.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultDetailMovie
    }

    fun getTv(id: Int): LiveData<ApiResponse<DetailTvResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTv = MutableLiveData<ApiResponse<DetailTvResponse>>()
        client.buildServiceClient()
            .getTv(id, token)
            .enqueue(object : Callback<DetailTvResponse> {
                override fun onResponse(
                    call: Call<DetailTvResponse>,
                    response: Response<DetailTvResponse>
                ) {
                    Log.d("TvGet", response.body().toString())
                    response.body()?.let {
                        resultDetailTv.value = ApiResponse.success(it)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailTvResponse>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    resultDetailTv.value = ApiResponse.error(t.message.toString(), null)
                    EspressoIdlingResource.decrement()
                }

            })
        return resultDetailTv
    }
}