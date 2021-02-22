package com.hanifabdullah.data.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.ActionBar
import com.hanifabdullah.data.R

class DialogLoading(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }
}