package com.aemiralfath.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity.Companion.TABLE_NAME

object SortUtils {
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val CHARACTER = "CHARACTER"

    fun getSortedQuery(filter: String, table: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table ")
        when (filter) {
            NEWEST -> {
                if (table == TABLE_NAME) {
                    simpleQuery.append("ORDER BY releaseDate DESC")
                } else {
                    simpleQuery.append("ORDER BY firstAirDate DESC")
                }
            }
            OLDEST -> {
                if (table == TABLE_NAME) {
                    simpleQuery.append("ORDER BY releaseDate ASC")
                } else {
                    simpleQuery.append("ORDER BY firstAirDate ASC")
                }
            }
            CHARACTER -> {
                if (table == TABLE_NAME) {
                    simpleQuery.append("ORDER BY title ASC")
                } else {
                    simpleQuery.append("ORDER BY name ASC")
                }
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}