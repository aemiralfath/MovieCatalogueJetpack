package com.aemiralfath.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity

class MovieViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getMovie(): LiveData<List<MovieEntity>> = movieRepository.getAllMovies()

}