package com.aemiralfath.moviecatalogue.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity

@Database(
    entities = [MovieEntity::class, TvEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase() : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}