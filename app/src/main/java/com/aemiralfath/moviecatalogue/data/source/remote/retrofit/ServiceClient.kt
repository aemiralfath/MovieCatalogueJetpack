package com.aemiralfath.moviecatalogue.data.source.remote.retrofit

import com.aemiralfath.moviecatalogue.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceClient {

    private var builder = Retrofit.Builder()

    private fun getInterceptor(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(logging).build()
        } else {
            OkHttpClient.Builder().build()
        }
    }

    fun buildServiceClient(): ServiceRepository {
        return builder.baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
            .create(ServiceRepository::class.java)
    }
}