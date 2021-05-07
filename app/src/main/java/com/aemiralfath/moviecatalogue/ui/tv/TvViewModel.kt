package com.aemiralfath.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.vo.Resource

class TvViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getTv(sort: String, query: String): LiveData<Resource<PagedList<TvEntity>>> =
        movieRepository.getAllTv(sort, query)

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = movieRepository.getFavoriteTv()

}