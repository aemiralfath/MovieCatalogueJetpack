package com.aemiralfath.moviecatalogue.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.vo.Resource

class DetailTvViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getTv(id: Int): LiveData<Resource<TvEntity>> = movieRepository.getTv(id)

}