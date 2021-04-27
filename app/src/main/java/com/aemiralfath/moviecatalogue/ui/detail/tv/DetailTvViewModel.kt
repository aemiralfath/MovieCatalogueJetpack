package com.aemiralfath.moviecatalogue.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MovieRepository
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity

class DetailTvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTv(id: Int): LiveData<TvEntity> = movieRepository.getTv(id)

}