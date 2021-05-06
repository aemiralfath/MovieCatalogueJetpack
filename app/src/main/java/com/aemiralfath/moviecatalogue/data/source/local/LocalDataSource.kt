package com.aemiralfath.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getMovie()

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getFavoriteMovie()

    fun getAllTv(): DataSource.Factory<Int, TvEntity> = movieDao.getTv()

    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity> = movieDao.getFavoriteTv()

    fun getMovieById(id: Int): LiveData<MovieEntity> = movieDao.getMovieById(id)

    fun getTvById(id: Int): LiveData<TvEntity> = movieDao.getTvById(id)

    fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun insertTv(tv: List<TvEntity>) = movieDao.insertTv(tv)

}