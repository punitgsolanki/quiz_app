package com.example.quizdemo.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.quizdemo.R
import com.example.quizdemo.utils.extensions.hideView
import com.example.quizdemo.utils.extensions.invisibleView
import com.example.quizdemo.utils.extensions.nullSafe
import com.example.quizdemo.utils.extensions.showView
import com.google.android.material.snackbar.Snackbar

object SnackBarUtils {
    fun showCustomSnackBar(
        context: Context?,
        rootView: View,
        mMessage: String,
        type: Int? = SNACK_BAR_SUCCESS,
        isActionButtonNeeded: Boolean? = true
    ): Snackbar {
        val snackbar = Snackbar.make(
            rootView,
            "",
            if (isActionButtonNeeded!!) Snackbar.LENGTH_INDEFINITE else 3000
        )
        val layout = snackbar.view as Snackbar.SnackbarLayout

        val textView = layout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.invisibleView()
        layout.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTransparent))

        val snackView = LayoutInflater.from(context).inflate(R.layout.custom_snack_bar, null)

        val llParent = snackView.findViewById<LinearLayout>(R.id.llParentView)
        val tvText = snackView.findViewById<AppCompatTextView>(R.id.tvMessageText)
        val tvAction = snackView.findViewById<AppCompatTextView>(R.id.tvAction)

        if (isActionButtonNeeded.nullSafe()) {
            tvAction.showView()
        } else {
            tvAction.hideView()
        }

        if (type == SNACK_BAR_SUCCESS) {
            tvAction.setTextColor(ContextCompat.getColor(context, R.color.colorSnackBarGreen))
            llParent.background = ContextCompat.getDrawable(context, R.drawable.bg_green_round)
        } else {
            tvAction.setTextColor(ContextCompat.getColor(context, R.color.colorSnackBarRed))
            llParent.background = ContextCompat.getDrawable(context, R.drawable.bg_red_round)
        }

        tvText.text = mMessage

        tvAction.setOnClickListener {
            snackbar.dismiss()
        }

        val params = layout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackView.layoutParams = params

//        layout.setPadding(10, 10, 10, 10)
        layout.addView(snackView, 0)

        return snackbar
    }
}

