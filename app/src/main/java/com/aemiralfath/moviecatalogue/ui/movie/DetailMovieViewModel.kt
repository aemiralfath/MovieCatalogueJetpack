package com.aemiralfath.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.entity.ResultsItemMovie

class DetailMovieViewModel: ViewModel() {
    private lateinit var movie: ResultsItemMovie

    fun setMovie(movie: ResultsItemMovie) {
        this.movie = movie
    }

    fun getMovie(): ResultsItemMovie {
        return movie
    }
}