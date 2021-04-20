package com.aemiralfath.moviecatalogue.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.entity.ResultsItemMovie
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailMovieViewModel::class.java).apply {
            intent.getParcelableExtra<ResultsItemMovie>(EXTRA_MOVIE)?.let { setMovie(it) }
        }

        val movie = viewModel.getMovie()
        val rating = "${movie.voteAverage.toString()}/10"

        supportActionBar?.title = movie.originalTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            tvTitle.text = movie.originalTitle
            tvMovieDate.text = movie.releaseDate
            tvMovieRating.text = rating
            tvOverview.text = movie.overview
            tvMovieAdult.text = if (movie.adult == true) "Yes" else "No"
            tvMovieLanguage.text = movie.originalLanguage
            tvMoviePopularity.text = movie.popularity.toString()
            tvMovieVote.text = movie.voteCount.toString()

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgMoviePoster)
        }
    }
}