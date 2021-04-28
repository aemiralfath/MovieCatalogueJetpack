package com.aemiralfath.moviecatalogue.ui.detail.tv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailTvBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var binding: ActivityDetailTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: DetailTvViewModel by viewModel()

        val id = intent.getParcelableExtra<TvEntity>(EXTRA_TV)?.id

        if (id != null) {
            showLoading(true)
            viewModel.getTv(id).observe(this, { tv ->
                showLoading(false)
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
            })
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}