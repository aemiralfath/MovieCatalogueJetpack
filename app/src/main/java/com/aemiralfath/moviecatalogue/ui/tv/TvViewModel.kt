package com.aemiralfath.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.MainRepository
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.utils.Resource

class TvViewModel(private val movieRepository: MainRepository) : ViewModel() {

    fun getTv(): LiveData<Resource<List<TvEntity>>> = movieRepository.getAllTv()

}