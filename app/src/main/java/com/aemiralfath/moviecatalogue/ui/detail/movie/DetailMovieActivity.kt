package com.aemiralfath.moviecatalogue.ui.detail.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.aemiralfath.moviecatalogue.di.ViewModelFactory
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
            ViewModelFactory.getInstance()
        ).get(DetailMovieViewModel::class.java)

        val id = intent.getParcelableExtra<MovieEntity>(EXTRA_MOVIE)?.id

        if (id != null) {
            showLoading(true)
            viewModel.getMovie(id).observe(this, { movie ->
                showLoading(false)
                val rating = "${movie.voteAverage.toString()}/10"

                supportActionBar?.title = movie.title
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                with(binding) {
                    tvMovieTitle.text = movie.title
                    tvMovieDate.text = movie.releaseDate
                    tvMovieRating.text = rating
                    tvOverview.text = movie.overview
                    tvMovieAdult.text = if (movie.adult == true) "Yes" else "No"
                    tvMovieLanguage.text = movie.originalLanguage
                    tvMoviePopularity.text = movie.popularity.toString()
                    tvMovieVote.text = movie.voteCount.toString()

                    Glide.with(this@DetailMovieActivity)
                        .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgMoviePoster)

                    btnMovieShare.setOnClickListener {
                        ShareCompat.IntentBuilder
                            .from(this@DetailMovieActivity)
                            .setType("text/plain")
                            .setChooserTitle(R.string.share_tv)
                            .setText(resources.getString(R.string.share_text, movie.title))
                            .startChooser()
                    }
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}