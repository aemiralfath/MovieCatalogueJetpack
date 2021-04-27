package com.aemiralfath.moviecatalogue.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    val overview: String? = null,

    val originalLanguage: String? = null,

    val title: String? = null,

    val posterPath: String? = null,

    val releaseDate: String? = null,

    val popularity: Double? = null,

    val voteAverage: Double? = null,

    val id: Int? = null,

    val adult: Boolean? = null,

    val voteCount: Int? = null
) : Parcelable