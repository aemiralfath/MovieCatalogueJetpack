package com.aemiralfath.moviecatalogue.di

import android.app.Application
import androidx.room.Room
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.LocalDataSource
import com.aemiralfath.moviecatalogue.data.source.local.room.MovieDao
import com.aemiralfath.moviecatalogue.data.source.local.room.MovieDatabase
import com.aemiralfath.moviecatalogue.data.source.remote.RemoteDataSource
import com.aemiralfath.moviecatalogue.data.source.remote.retrofit.ServiceClient
import com.aemiralfath.moviecatalogue.ui.detail.movie.DetailMovieViewModel
import com.aemiralfath.moviecatalogue.ui.detail.tv.DetailTvViewModel
import com.aemiralfath.moviecatalogue.ui.movie.MovieViewModel
import com.aemiralfath.moviecatalogue.ui.tv.TvViewModel
import com.aemiralfath.moviecatalogue.utils.AppExecutors
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ServiceClient() }
    single { RemoteDataSource(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(
            application,
            MovieDatabase::class.java,
            "Movies.db"
        ).fallbackToDestructiveMigration().build()
    }

    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
    single { LocalDataSource(get()) }
}

val appModule = module {
    single { AppExecutors() }
    single { MainRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { DetailTvViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}