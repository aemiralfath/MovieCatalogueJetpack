package com.aemiralfath.moviecatalogue.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.entity.ResultsItemMovie
import com.aemiralfath.moviecatalogue.databinding.ItemRowBinding
import com.aemiralfath.moviecatalogue.ui.movie.DetailMovieActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovie = MovieEntity()

    fun setMovie(movie: MovieEntity) {
        if (movie.results?.isEmpty() == true) return
        this.listMovie = movie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie.results?.get(position))
    }

    override fun getItemCount(): Int = listMovie.results?.size ?: 0

    class MovieViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultsItemMovie?) {
            with(binding) {
                tvItemTitle.text = movie?.originalTitle
                tvItemDate.text = movie?.releaseDate
                tvItemDescription.text = movie?.overview

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}