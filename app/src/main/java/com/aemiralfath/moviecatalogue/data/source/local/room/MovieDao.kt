package com.aemiralfath.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntities")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieEntities WHERE favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM TvEntities")
    fun getTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM TvEntities WHERE favorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM MovieEntities WHERE id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM TvEntities WHERE id = :id")
    fun getTvById(id: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tv: List<TvEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTv(tv: TvEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovieQuery(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getTvQuery(query: SupportSQLiteQuery): DataSource.Factory<Int, TvEntity>

}