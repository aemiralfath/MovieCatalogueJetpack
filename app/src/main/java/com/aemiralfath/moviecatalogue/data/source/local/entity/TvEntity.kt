package com.aemiralfath.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TvEntities")
data class TvEntity(
    var firstAirDate: String? = null,

    var overview: String? = null,

    var originalLanguage: String? = null,

    var posterPath: String? = null,

    var popularity: Double? = null,

    var voteAverage: Double? = null,

    var name: String? = null,

    @PrimaryKey
    @NonNull
    var id: Int,

    var voteCount: Int? = null,

    var favorite: Boolean = false
) : Parcelable
