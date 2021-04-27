package com.aemiralfath.moviecatalogue.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.ui.home.HomeActivity
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val TIME = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, TIME)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}