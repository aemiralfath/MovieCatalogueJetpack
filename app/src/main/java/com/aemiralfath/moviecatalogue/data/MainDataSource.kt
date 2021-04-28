package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.Resource

interface MainDataSource {
    fun getAllMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getAllTv(): LiveData<Resource<List<TvEntity>>>

    fun getMovie(id: Int): LiveData<Resource<MovieEntity>>

    fun getTv(id: Int): LiveData<Resource<TvEntity>>
}