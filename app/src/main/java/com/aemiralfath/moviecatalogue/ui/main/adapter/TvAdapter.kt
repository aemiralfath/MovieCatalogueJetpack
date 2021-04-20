package com.aemiralfath.moviecatalogue.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.entity.ItemTvEntity
import com.aemiralfath.moviecatalogue.data.entity.TvEntity
import com.aemiralfath.moviecatalogue.databinding.ItemRowBinding
import com.aemiralfath.moviecatalogue.ui.detail.tv.DetailTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private var listTv = TvEntity()

    fun setTv(tv: TvEntity) {
        if (tv.results?.isEmpty() == true) return
        this.listTv = tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(listTv.results?.get(position))
    }

    override fun getItemCount(): Int = listTv.results?.size ?: 0

    class TvViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: ItemTvEntity?) {
            with(binding) {
                tvItemTitle.text = tv?.originalName
                tvItemDate.text = tv?.firstAirDate
                tvItemDescription.text = tv?.overview

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${tv?.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.EXTRA_TV, tv)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}