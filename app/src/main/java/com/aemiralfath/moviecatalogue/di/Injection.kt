package com.aemiralfath.moviecatalogue.di

import com.aemiralfath.moviecatalogue.data.MovieRepository
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.retrofit.ServiceClient

object Injection {

    fun provideRepository(): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ServiceClient())
        return MovieRepository.getInstance(remoteDataSource)
    }
}