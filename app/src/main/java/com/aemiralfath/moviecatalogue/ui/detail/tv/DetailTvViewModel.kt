package com.aemiralfath.moviecatalogue.ui.detail.tv

import androidx.lifecycle.ViewModel
import com.aemiralfath.moviecatalogue.data.entity.ItemTvEntity

class DetailTvViewModel : ViewModel() {
    private lateinit var tv: ItemTvEntity

    fun setTv(tv: ItemTvEntity) {
        this.tv = tv
    }

    fun getTv(): ItemTvEntity {
        return tv
    }
}