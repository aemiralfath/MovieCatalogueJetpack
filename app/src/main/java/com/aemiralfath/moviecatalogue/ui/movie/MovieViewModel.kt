package com.aemiralfath.moviecatalogue.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.BuildConfig.TMDB_API_KEY
import com.aemiralfath.moviecatalogue.data.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.network.ServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val token = TMDB_API_KEY
    private var dataMovie: MutableLiveData<MovieEntity> = MutableLiveData()

    fun setMovie() {
        ServiceClient().buildServiceClient()
            .getMovie(token)
            .enqueue(object : Callback<MovieEntity> {
                override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                    Log.d("MovieGet", response.body().toString())
                    dataMovie.postValue(response.body())
                }

                override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                }

            })
    }

    fun getMovie(): LiveData<MovieEntity> = dataMovie
}