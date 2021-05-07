package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.vo.Resource

interface MainDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getMovie(id: Int): LiveData<Resource<MovieEntity>>

    fun getTv(id: Int): LiveData<Resource<TvEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTvFavorite(tv: TvEntity, state: Boolean)

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>>
}