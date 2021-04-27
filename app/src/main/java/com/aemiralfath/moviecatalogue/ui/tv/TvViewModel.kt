package com.aemiralfath.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MovieRepository
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTv(): LiveData<List<TvEntity>> = movieRepository.getAllTv()

}