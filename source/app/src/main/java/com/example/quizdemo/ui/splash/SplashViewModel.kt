package com.example.quizdemo.ui.splash

import android.app.Application
import com.example.quizdemo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application,
) : BaseViewModel(application)
