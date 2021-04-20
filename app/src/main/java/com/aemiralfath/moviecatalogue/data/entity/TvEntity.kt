package com.aemiralfath.moviecatalogue.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvEntity(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ItemTvEntity?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
) : Parcelable