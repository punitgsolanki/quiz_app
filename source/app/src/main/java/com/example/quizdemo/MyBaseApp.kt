package com.example.quizdemo

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference


@HiltAndroidApp
class MyBaseApp : MultiDexApplication() {
    private var _currentActivity: WeakReference<Activity>? = null

    val currentActivity: Activity?
        get() = _currentActivity?.get()

    fun setCurrentActivity(activity: Activity?) {
        _currentActivity = if (activity != null) WeakReference(activity) else null
    }

    companion object {
        var instance: MyBaseApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}