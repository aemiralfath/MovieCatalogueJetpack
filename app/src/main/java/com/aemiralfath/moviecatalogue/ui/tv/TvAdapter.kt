package com.aemiralfath.moviecatalogue.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.databinding.ItemRowBinding
import com.aemiralfath.moviecatalogue.ui.detail.tv.DetailTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter : PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
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
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }

    class TvViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                tvItemTitle.text = tv.name
                tvItemDate.text = tv.firstAirDate
                tvItemDescription.text = tv.overview

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${tv.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.EXTRA_TV, tv.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}