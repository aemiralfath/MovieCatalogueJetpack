package com.aemiralfath.moviecatalogue.ui.detail.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.aemiralfath.moviecatalogue.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private var id: Int? = null
    private var menu: Menu? = null
    private var movieEntity: MovieEntity? = null

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getParcelableExtra<MovieEntity>(EXTRA_MOVIE)?.id

        id?.let {
            viewModel.getMovie(it).observe(this, { movie ->
                when (movie.status) {
                    Status.SUCCESS -> {
                        showLoading(false)
                        movie.data?.let { data ->
                            populateView(data)
                            movieEntity = data
                        }
                    }
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(this, movie.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun populateView(movie: MovieEntity) {
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        id?.let {
            viewModel.getMovie(it).observe(this, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> if (movie.data != null) {
                            showLoading(false)
                            setBookmarkState(movie.data.favorite)
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(
                                applicationContext,
                                "Terjadi kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_favorite -> {
                movieEntity?.let { movie ->
                    viewModel.setFavorite(movie)
                    val state = !movie.favorite
                    setBookmarkState(state)
                    if (state) {
                        Snackbar.make(binding.root, "Add to Favorite", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(binding.root, "Remove Favorite", Snackbar.LENGTH_LONG).show()
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}