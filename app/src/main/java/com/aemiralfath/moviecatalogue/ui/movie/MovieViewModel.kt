package com.aemiralfath.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.utils.Resource

class MovieViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getMovie(): LiveData<Resource<List<MovieEntity>>> = movieRepository.getAllMovies()

}