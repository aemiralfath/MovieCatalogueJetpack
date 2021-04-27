package com.aemiralfath.moviecatalogue.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvEntity(
    val firstAirDate: String? = null,

    val overview: String? = null,

    val originalLanguage: String? = null,

    val posterPath: String? = null,

    val popularity: Double? = null,

    val voteAverage: Double? = null,

    val name: String? = null,

    val id: Int? = null,

    val voteCount: Int? = null
) : Parcelable
