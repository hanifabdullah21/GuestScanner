package com.hanifabdullah.guestscanner.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import com.hanifabdullah.guestscanner.ui.guestScan.GuestScanActivity
import com.hanifabdullah.guestscanner.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setupNavigation()

        dash_fab.setOnClickListener {
            startActivity(Intent(this, GuestScanActivity::class.java))
        }

        dash_iv_back.setOnClickListener {
            DialogMessage(this)
                .setTitle("Perhatian")
                .setMessage("Apakah anda yakin ingin logout?")
                .setTextButtonPrimary("Ya")
                .setTextButtonSecondary("Batal")
                .setListenerButtonPrimary(object : DialogMessageListener {
                    override fun onClick(dialogMessage: DialogMessage) {
                        dialogMessage.dismiss()
                        repository.pref?.userLoggedOut()

                        finish()
                        val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }).showDialog()
        }

        splash_iv_digimedia.setOnClickListener {
            profilDigimedia()
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.dash_fragment)
        NavigationUI.setupWithNavController(dash_bottomnav, navController)
    }
}