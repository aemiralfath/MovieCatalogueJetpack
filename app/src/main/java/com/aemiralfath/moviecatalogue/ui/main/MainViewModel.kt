package com.aemiralfath.moviecatalogue.ui.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.BuildConfig.TMDB_API_KEY
import com.aemiralfath.moviecatalogue.data.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.network.ServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val token = TMDB_API_KEY
    private var dataMovie: MutableLiveData<MovieEntity> = MutableLiveData()
    private var dataTv: MutableLiveData<TvEntity> = MutableLiveData()

    fun setMovie(context: Context) {
        ServiceClient().buildServiceClient()
            .getMovie(token)
            .enqueue(object : Callback<MovieEntity> {
                override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                    Log.d("MovieGet", response.body().toString())
                    dataMovie.postValue(response.body())
                }

                override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                    Log.d("MovieGet", "Fail")
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun setTv(context: Context) {
        ServiceClient().buildServiceClient()
            .geTvShow(token)
            .enqueue(object : Callback<TvEntity> {
                override fun onResponse(call: Call<TvEntity>, response: Response<TvEntity>) {
                    Log.d("TvGet", response.body().toString())
                    dataTv.postValue(response.body())
                }

                override fun onFailure(call: Call<TvEntity>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getMovie(): LiveData<MovieEntity> = dataMovie

    fun getTv(): LiveData<TvEntity> = dataTv

}