package com.aemiralfath.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity

class DetailMovieViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getMovie(id: Int): LiveData<MovieEntity> = movieRepository.getMovie(id)
}