package com.example.quizdemo.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.example.quizdemo.MyBaseApp
import com.example.quizdemo.utils.ActivityLauncher
import com.example.quizdemo.utils.ActivityLauncher.registerActivityForResult
import com.example.quizdemo.utils.SNACK_BAR_ERROR
import com.example.quizdemo.utils.SnackBarUtils
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {


    protected abstract fun getViewBinding(): VB

    protected abstract fun listeners()

    protected abstract fun initSetup()

    protected abstract fun onDestroyActivity()

    protected lateinit var binding: VB

    protected open fun addObservers() {/*Add observer in this method*/
    }

    protected open fun removeObservers() {/*Remove observer in this method*/
    }

    protected lateinit var activityLauncher: ActivityLauncher<Intent, ActivityResult>

    private val _deviceInfoId = MutableLiveData<String?>()
    val deviceInfoId: LiveData<String?> get() = _deviceInfoId


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLauncher = registerActivityForResult(this)

        binding = getViewBinding()
        setContentView(binding.root)

        addObservers()

        initSetup()
        listeners()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    fun setUpToolbar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private var snackbar: Snackbar? = null

    /**
     * Display the snack bar
     */
    fun showSnackBar(
        message: String,
        type: Int? = SNACK_BAR_ERROR,
        isActionButtonNeeded: Boolean? = false
    ) {
        snackbar = SnackBarUtils.showCustomSnackBar(
            this,
            findViewById(android.R.id.content),
            message,
            type,
            isActionButtonNeeded
        )
        snackbar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
        onDestroyActivity()
    }

    override fun onResume() {
        super.onResume()
        (application as MyBaseApp).setCurrentActivity(this)
    }

    override fun onPause() {
        super.onPause()
        if ((application as MyBaseApp).currentActivity == this) {
            (application as MyBaseApp).setCurrentActivity(null)
        }
    }
}
