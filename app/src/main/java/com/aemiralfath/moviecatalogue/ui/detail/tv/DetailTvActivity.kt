package com.aemiralfath.moviecatalogue.ui.detail.tv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailTvBinding
import com.aemiralfath.moviecatalogue.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    private var id: Int? = null
    private var menu: Menu? = null
    private var tvEntity: TvEntity? = null

    private lateinit var binding: ActivityDetailTvBinding
    private val viewModel: DetailTvViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(EXTRA_TV, 0)

        id?.let {
            viewModel.getTv(it).observe(this, { tv ->
                when (tv.status) {
                    Status.SUCCESS -> {
                        showLoading(false)
                        tv.data?.let { data ->
                            populateView(data)
                            tvEntity = data
                        }
                    }
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(this, tv.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun populateView(tv: TvEntity) {
        val rating = "${tv.voteAverage.toString()}/10"

        supportActionBar?.title = tv.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            tvTvTitle.text = tv.name
            tvTvDate.text = tv.firstAirDate
            tvTvRating.text = rating
            tvOverview.text = tv.overview
            tvTvLanguage.text = tv.originalLanguage
            tvTvPopularity.text = tv.popularity.toString()
            tvTvVote.text = tv.voteCount.toString()

            Glide.with(this@DetailTvActivity)
                .load("https://image.tmdb.org/t/p/w500${tv.posterPath}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgTvPoster)

            btnTvShare.setOnClickListener {
                ShareCompat.IntentBuilder
                    .from(this@DetailTvActivity)
                    .setType("text/plain")
                    .setChooserTitle(R.string.share_movie)
                    .setText(resources.getString(R.string.share_text, tv.name))
                    .startChooser()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        id?.let {
            viewModel.getTv(it).observe(this, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> if (tv.data != null) {
                            showLoading(false)
                            setBookmarkState(tv.data.favorite)
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
                tvEntity?.let { tv ->
                    viewModel.setFavorite(tv)
                    val state = !tv.favorite
                    setBookmarkState(tv.favorite)
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