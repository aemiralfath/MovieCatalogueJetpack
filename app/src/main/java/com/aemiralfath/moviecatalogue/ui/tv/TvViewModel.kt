package com.aemiralfath.moviecatalogue.ui.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.BuildConfig.TMDB_API_KEY
import com.aemiralfath.moviecatalogue.data.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.network.ServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvViewModel : ViewModel() {

    private val token = TMDB_API_KEY
    private var dataTv: MutableLiveData<TvEntity> = MutableLiveData()


    fun setTv() {
        ServiceClient().buildServiceClient()
            .geTvShow(token)
            .enqueue(object : Callback<TvEntity> {
                override fun onResponse(call: Call<TvEntity>, response: Response<TvEntity>) {
                    Log.d("TvGet", response.body().toString())
                    dataTv.postValue(response.body())
                }

                override fun onFailure(call: Call<TvEntity>, t: Throwable) {
                    Log.d("TvGet", "Fail")
                }

            })
    }

    fun getTv(): LiveData<TvEntity> = dataTv

}