package com.aemiralfath.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.vo.Resource

class TvViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getTv(): LiveData<Resource<PagedList<TvEntity>>> = movieRepository.getAllTv()

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = movieRepository.getFavoriteTv()

}