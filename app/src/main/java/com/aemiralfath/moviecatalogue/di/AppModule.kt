package com.aemiralfath.moviecatalogue.di

import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.remote.retrofit.ServiceClient
import com.aemiralfath.moviecatalogue.ui.detail.movie.DetailMovieViewModel
import com.aemiralfath.moviecatalogue.ui.detail.tv.DetailTvViewModel
import com.aemiralfath.moviecatalogue.ui.movie.MovieViewModel
import com.aemiralfath.moviecatalogue.ui.tv.TvViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RemoteDataSource(ServiceClient()) }
    single {
        val remoteDataSource: RemoteDataSource = get()
        MainRepository(remoteDataSource)
    }
}

val viewModelModule = module {
    viewModel {
        val repository: MainRepository = get()
        MovieViewModel(repository)
    }
    viewModel {
        val repository: MainRepository = get()
        TvViewModel(repository)
    }
    viewModel {
        val repository: MainRepository = get()
        DetailTvViewModel(repository)
    }
    viewModel {
        val repository: MainRepository = get()
        DetailMovieViewModel(repository)
    }
}