package com.hanifabdullah.guestscanner.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.Result
import com.hanifabdullah.data.model.StatusResponse
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import com.hanifabdullah.guestscanner.ui.invitation.InvitationActivity
import com.hanifabdullah.guestscanner.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    lateinit var viewModelFactory: MainViewModelFactory
    lateinit var viewModel: MainViewModel

    lateinit var zXingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = MainViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setupObserver()

        zXingScannerView = ZXingScannerView(this@MainActivity)
        zXingScannerView.setAspectTolerance(0.5f);
        main_scanner.addView(zXingScannerView)

        zXingScannerView.startCamera()
        zXingScannerView.setResultHandler(this@MainActivity)

        main_btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        splash_iv_digimedia.setOnClickListener {
            profilDigimedia()
        }
    }

    private fun setupObserver() {
        viewModel.guest.observe(this, Observer {
            when (it.statusRequest) {
                StatusResponse.LOADING -> loading.show()
                StatusResponse.SUCCESS -> {
                    loading.dismiss()
                    viewModel.saveGuest(it.data)

                    val intent = Intent(this, InvitationActivity::class.java)
                    startActivity(intent)
                }
                StatusResponse.ERROR -> {
                    loading.dismiss()
                    DialogMessage(this)
                        .setTitle("Ups...")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Scan Ulang")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                zXingScannerView.startCamera()
                                zXingScannerView.setResultHandler(this@MainActivity)
                            }
                        })
                        .setDialogCancelable(false)
                        .showDialog()
                }
            }
        })
    }

    override fun handleResult(rawResult: Result?) {
        viewModel.getGuestById(rawResult?.text)
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView.setResultHandler(this)
        zXingScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView.stopCamera()
    }

}