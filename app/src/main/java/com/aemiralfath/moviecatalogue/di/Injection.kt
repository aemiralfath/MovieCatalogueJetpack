package com.aemiralfath.moviecatalogue.di

import android.content.Context
import com.aemiralfath.moviecatalogue.data.MovieRepository
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.retrofit.ServiceClient

object Injection {

    fun provideRepository(context: Context): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ServiceClient())
        return MovieRepository.getInstance(remoteDataSource)
    }
}