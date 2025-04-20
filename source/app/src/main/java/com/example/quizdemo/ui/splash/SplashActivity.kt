package com.example.quizdemo.ui.splash

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import com.example.quizdemo.databinding.ActivitySplashBinding
import com.example.quizdemo.ui.base.BaseActivity
import com.example.quizdemo.ui.home.HomeActivity.Companion.callHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initSetup() {
        // Start the delayed flow to navigate to the HomeActivity
        lifecycleScope.launch {
            delay(2000) // Wait for 2 seconds
            navigateToHome()
        }
    }

    override fun onDestroyActivity() {
    }

    override fun listeners() {

    }

    override fun addObservers() {
    }

    override fun removeObservers() {
    }

    private fun navigateToHome() {
        activityLauncher.launch(callHomeActivity(this))
        finish()
    }
}