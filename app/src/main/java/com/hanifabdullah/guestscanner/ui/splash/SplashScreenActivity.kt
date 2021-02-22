package com.hanifabdullah.guestscanner.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import com.hanifabdullah.guestscanner.ui.dashboard.DashboardActivity
import com.hanifabdullah.guestscanner.ui.invitation.InvitationActivity
import com.hanifabdullah.guestscanner.ui.main.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : BaseActivity() {

    lateinit var viewModelFactory: SplashScreenViewModelFactory
    lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModelFactory = SplashScreenViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        Handler().postDelayed(Runnable {
            checkCameraPermission()
        }, 2500)

        splash_iv_digimedia.setOnClickListener { profilDigimedia() }

    }

    private fun checkCameraPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    checkIsUserLoggedIn()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    DialogMessage(this@SplashScreenActivity)
                        .setTitle("Perhatian")
                        .setMessage("Berikan ijin untuk dapat menggunakan aplikasi ini")
                        .setTextButtonPrimary("Ok")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                checkCameraPermission()
                            }
                        }).showDialog()
                }
            }).check()
    }

    private fun checkIsUserLoggedIn() {
        if (viewModel.isLoggedIn == true) {
            if (viewModel.isAdmin == true) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, InvitationActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}