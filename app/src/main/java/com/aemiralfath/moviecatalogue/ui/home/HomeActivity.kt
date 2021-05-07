package com.aemiralfath.moviecatalogue.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.databinding.ActivityHomeBinding
import com.aemiralfath.moviecatalogue.ui.favorite.FavoriteActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homePagerAdapter = HomePagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = homePagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_list_favorite) {
            startActivity(Intent(this, FavoriteActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}