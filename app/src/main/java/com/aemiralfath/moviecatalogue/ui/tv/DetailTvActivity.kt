package com.aemiralfath.moviecatalogue.ui.tv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.entity.ResultsItemTv
import com.aemiralfath.moviecatalogue.databinding.ActivityDetailTvBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var binding: ActivityDetailTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailTvViewModel::class.java).apply {
            intent.getParcelableExtra<ResultsItemTv>(EXTRA_TV)?.let { setTv(it) }
        }

        val tv = viewModel.getTv()
        val rating = "${tv.voteAverage.toString()}/10"

        supportActionBar?.title = tv.originalName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            tvTitle.text = tv.originalName
            tvTvDate.text = tv.firstAirDate
            tvTvRating.text = rating
            tvOverview.text = tv.overview
            tvTvLanguage.text = tv.originalLanguage
            tvTvPopularity.text = tv.popularity.toString()
            tvTvVote.text = tv.voteCount.toString()

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${tv.posterPath}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgTvPoster)
        }
    }
}