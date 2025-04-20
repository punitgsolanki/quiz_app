package com.example.quizdemo.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.quizdemo.R
import com.example.quizdemo.utils.extensions.hideView
import com.example.quizdemo.utils.extensions.showView


object DialogUtils {

    fun showInfoDialog(
        context: Context,
        message: String = context.getString(R.string.lbl_something_went_wrong),
        positiveText: String = context.getString(R.string.btn_text_ok),
        icon: Int? = R.drawable.icn_info,
        callback: DialogInterface.OnClickListener? = null
    ) {
        val errorAlertDialog = Dialog(context)
        errorAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        errorAlertDialog.setContentView(R.layout.dialog_one_button)

        if (icon != null) {
            errorAlertDialog.findViewById<AppCompatImageView>(R.id.ivIcon).showView()
            errorAlertDialog.findViewById<AppCompatImageView>(R.id.ivIcon).setImageResource(icon)
        } else {
            errorAlertDialog.findViewById<AppCompatImageView>(R.id.ivIcon).hideView()
        }

        errorAlertDialog.findViewById<AppCompatTextView>(R.id.tvMessage).text = message
        errorAlertDialog.findViewById<AppCompatButton>(R.id.btPositive).text = positiveText

        errorAlertDialog.findViewById<AppCompatButton>(R.id.btPositive).setOnClickListener {
            errorAlertDialog.dismiss()
            callback?.onClick(errorAlertDialog, DialogInterface.BUTTON_POSITIVE)
        }

        errorAlertDialog.show()
        errorAlertDialog.window!!.setLayout(
            context.resources.getDimension(R.dimen.confirmation_dialog_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}