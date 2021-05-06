package com.aemiralfath.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.vo.Resource

class MovieViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getMovie(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getAllMovies()

}