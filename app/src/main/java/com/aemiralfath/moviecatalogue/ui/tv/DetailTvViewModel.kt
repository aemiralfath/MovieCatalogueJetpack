package com.aemiralfath.moviecatalogue.ui.tv

import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.entity.ResultsItemTv

class DetailTvViewModel : ViewModel() {
    private lateinit var tv: ResultsItemTv

    fun setTv(tv: ResultsItemTv) {
        this.tv = tv
    }

    fun getTv(): ResultsItemTv {
        return tv
    }
}