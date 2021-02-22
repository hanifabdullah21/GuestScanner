package com.hanifabdullah.data.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBar
import com.hanifabdullah.data.R
import kotlinx.android.synthetic.main.dialog_message.*

class DialogMessage(context: Context) : Dialog(context) {

    var imgUrl: String? = null
    var imgDrawable: Int? = null
    var strTitle: String? = null
    var strMessage: String? = null
    var strButtonPrimary: String? = null
    var listenerButtonPrimary: DialogMessageListener? = null
    var isDialogCancelable: Boolean = true
    var strButtonSecondary: String? = null
    var listenerButtonSecondary: DialogMessageListener? = null

    init {
        strTitle = "Informasi"
        strMessage = "Terjadi Kesalahan"
        strButtonPrimary = "Ok"
        strButtonSecondary = null
        listenerButtonPrimary = object :
            DialogMessageListener {
            override fun onClick(dialogMessage: DialogMessage) {
                dismiss()
            }
        }
        listenerButtonSecondary = object :
            DialogMessageListener {
            override fun onClick(dialogMessage: DialogMessage) {
                dismiss()
            }
        }
        isDialogCancelable = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_message)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

        if (imgUrl != null || imgDrawable != null) {
            dm_iv.visibility = View.VISIBLE
            if (imgDrawable!=null) dm_iv.setImageResource(imgDrawable!!)
        } else {
            dm_iv.visibility = View.GONE
        }

        dm_tv_title.text = strTitle
        dm_tv_message.text = strMessage

        dm_btn_primary.text = strButtonPrimary
        dm_btn_primary.setOnClickListener {
            listenerButtonPrimary?.onClick(this)
        }

        dm_btn_primary.text = strButtonPrimary
        dm_btn_primary.setOnClickListener {
            listenerButtonPrimary?.onClick(this)
        }

        dm_btn_secondary.visibility = if (strButtonSecondary != null) View.VISIBLE else View.GONE
        dm_btn_secondary.text = strButtonSecondary
        dm_btn_secondary.setOnClickListener {
            listenerButtonSecondary?.onClick(this)
        }

        this.setCancelable(isDialogCancelable)

    }

    fun setImageDrawable(img: Int?): DialogMessage{
        imgDrawable = img
        return this
    }

    fun setTitle(text: String?): DialogMessage {
        strTitle = text
        return this
    }

    fun setMessage(text: String?): DialogMessage {
        strMessage = text
        return this
    }

    fun setTextButtonPrimary(text: String): DialogMessage {
        strButtonPrimary = text
        return this
    }

    fun setListenerButtonPrimary(listener: DialogMessageListener): DialogMessage {
        listenerButtonPrimary = listener
        return this
    }

    fun setTextButtonSecondary(text: String): DialogMessage {
        strButtonSecondary = text
        return this
    }

    fun setListenerButtonSecondary(listener: DialogMessageListener): DialogMessage {
        listenerButtonSecondary = listener
        return this
    }

    fun setDialogCancelable(value: Boolean): DialogMessage {
        isDialogCancelable = value
        return this
    }

    fun showDialog() {
        show()
    }

}

interface DialogMessageListener {
    fun onClick(dialogMessage: DialogMessage)
}