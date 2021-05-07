package com.aemiralfath.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity

object SearchUtils {
    fun getSearchQuery(query: String, filter: String, table: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table ")
        if (table == MovieEntity.TABLE_NAME) {
            simpleQuery.append("WHERE title LIKE '%$query%' ")
        } else {
            simpleQuery.append("WHERE name LIKE '%$query%' ")
        }

        when (filter) {
            SortUtils.NEWEST -> {
                if (table == MovieEntity.TABLE_NAME) {
                    simpleQuery.append("ORDER BY releaseDate DESC")
                } else {
                    simpleQuery.append("ORDER BY firstAirDate DESC")
                }
            }
            SortUtils.OLDEST -> {
                if (table == MovieEntity.TABLE_NAME) {
                    simpleQuery.append("ORDER BY releaseDate ASC")
                } else {
                    simpleQuery.append("ORDER BY firstAirDate ASC")
                }
            }
            SortUtils.CHARACTER -> {
                if (table == MovieEntity.TABLE_NAME) {
                    simpleQuery.append("ORDER BY title ASC")
                } else {
                    simpleQuery.append("ORDER BY name ASC")
                }
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}