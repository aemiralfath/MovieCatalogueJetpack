package com.aemiralfath.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.entity.ItemMovieEntity

class DetailMovieViewModel: ViewModel() {
    private lateinit var movie: ItemMovieEntity

    fun setMovie(movie: ItemMovieEntity) {
        this.movie = movie
    }

    fun getMovie(): ItemMovieEntity {
        return movie
    }
}