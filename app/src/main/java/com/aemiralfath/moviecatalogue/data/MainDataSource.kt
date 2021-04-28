package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity

interface MainDataSource {
    fun getAllMovies(): LiveData<List<MovieEntity>>

    fun getAllTv(): LiveData<List<TvEntity>>

    fun getMovie(id: Int): LiveData<MovieEntity>

    fun getTv(id: Int): LiveData<TvEntity>
}