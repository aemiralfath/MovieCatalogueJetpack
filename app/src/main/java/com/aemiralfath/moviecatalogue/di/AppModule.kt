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
    single { MainRepository(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { DetailTvViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}