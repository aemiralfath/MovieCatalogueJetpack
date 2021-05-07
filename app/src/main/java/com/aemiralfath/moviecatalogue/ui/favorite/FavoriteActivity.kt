package com.aemiralfath.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aemiralfath.moviecatalogue.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoritePagerAdapter = FavoritePagerAdapter(this, supportFragmentManager)
        binding.viewPagerFavorite.adapter = favoritePagerAdapter
        binding.tabsFavorite.setupWithViewPager(binding.viewPagerFavorite)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite"
    }


}