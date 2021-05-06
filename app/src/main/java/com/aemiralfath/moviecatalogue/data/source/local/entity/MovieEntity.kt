package com.aemiralfath.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MovieEntities")
data class MovieEntity(
    var overview: String? = null,

    var originalLanguage: String? = null,

    var title: String? = null,

    var posterPath: String? = null,

    var releaseDate: String? = null,

    var popularity: Double? = null,

    var voteAverage: Double? = null,

    @PrimaryKey
    @NonNull
    var id: Int,

    var adult: Boolean? = null,

    var voteCount: Int? = null,

    var favorite: Boolean = false
) : Parcelable