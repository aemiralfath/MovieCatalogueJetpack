package com.aemiralfath.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.utils.Resource

class DetailMovieViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getMovie(id: Int): LiveData<Resource<MovieEntity>> = movieRepository.getMovie(id)
}