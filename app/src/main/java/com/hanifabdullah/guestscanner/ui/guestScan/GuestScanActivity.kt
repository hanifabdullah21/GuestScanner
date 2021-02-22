package com.hanifabdullah.guestscanner.ui.guestScan

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.zxing.Result
import com.hanifabdullah.data.model.StatusResponse
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_guest_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import kotlin.random.Random

class GuestScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    lateinit var guestScanViewModelFactory: GuestScanViewModelFactory
    lateinit var guestScanViewModel: GuestScanViewModel

    lateinit var zXingScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_scan)

        guestScanViewModelFactory = GuestScanViewModelFactory(application, repository)
        guestScanViewModel =
            ViewModelProvider(this, guestScanViewModelFactory).get(GuestScanViewModel::class.java)
        setupObserver()

        zXingScannerView = ZXingScannerView(this)
        zXingScannerView.setAspectTolerance(1F)
        zXingScannerView.setFormats(listOf(com.google.zxing.BarcodeFormat.QR_CODE))
        gsa_scanner.addView(zXingScannerView)

        startScanner()

        gsa_btn_rescan.setOnClickListener {
            gsa_btn_rescan.visibility = View.GONE
            gsa_cv.visibility = View.GONE

            startScanner()
        }

        gsa_btn_verif.setOnClickListener {
            guestScanViewModel.verification(guestScanViewModel.guest.value?.data?.id.toString())
        }

        gsa_iv_back.setOnClickListener { finish() }

        gsa_iv_digimedia.setOnClickListener {
            profilDigimedia()
        }
    }

    fun startScanner() {
        zXingScannerView.setResultHandler(this)
        zXingScannerView.startCamera()
    }

    private fun setupObserver() {
        guestScanViewModel.guest.observe(this, Observer {
            when (it.statusRequest) {
                StatusResponse.LOADING -> loading.show()
                StatusResponse.SUCCESS -> {
                    loading.dismiss()
                    if (it.data?.bridesId != guestScanViewModel.bridesId) {
                        DialogMessage(this)
                            .setTitle("Perhatian")
                            .setMessage("Undangan Tidak Cocok")
                            .setListenerButtonPrimary(object : DialogMessageListener {
                                override fun onClick(dialogMessage: DialogMessage) {
                                    dialogMessage.dismiss()
                                    startScanner()
                                }
                            })
                            .showDialog()
                    } else {
                        gsa_tv_name.text = it.data?.name
                        gsa_tv_from.text = it.data?.from
                        val random = Random.nextInt(1, 6)
                        var img: Int = R.drawable.avatar1
                        when (random) {
                            1 -> img = R.drawable.avatar1
                            2 -> img = R.drawable.avatar2
                            3 -> img = R.drawable.avatar3
                            4 -> img = R.drawable.avatar4
                            5 -> img = R.drawable.avatar5
                            6 -> img = R.drawable.avatar6
                        }
                        Glide.with(this)
                            .load(img)
                            .into(gsa_iv_guest)

                        gsa_btn_rescan.visibility = View.VISIBLE
                        gsa_cv.visibility = View.VISIBLE
                        gsa_btn_verif.visibility = View.VISIBLE

                        if (it.data?.isAttended == 1) gsa_btn_verif.visibility =
                            View.GONE else gsa_btn_verif.visibility = View.VISIBLE
                    }
                }
                StatusResponse.ERROR -> {
                    loading.dismiss()
                    DialogMessage(this)
                        .setTitle("Perhatian")
                        .setMessage("${it.failureModel?.msgSystem}")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()

                                gsa_btn_rescan.visibility = View.GONE
                                gsa_cv.visibility = View.GONE

                                startScanner()
                            }
                        })
                        .showDialog()
                }
            }
        })

        guestScanViewModel.verification.observe(this, Observer {
            when (it.statusRequest) {
                StatusResponse.LOADING ->loading.show()
                StatusResponse.SUCCESS -> {
                    loading.dismiss()
                    DialogMessage(this)
                        .setTitle("Sukses")
                        .setMessage("Tamu berhasil diverifikasi")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()

                                gsa_btn_rescan.visibility = View.GONE
                                gsa_cv.visibility = View.GONE

                                startScanner()
                            }
                        })
                        .showDialog()
                }
                StatusResponse.ERROR -> {
                    loading.dismiss()
                    Log.d("3RROR", "setupObserver: ${it.failureModel}")
                    DialogMessage(this)
                        .setTitle("Perhatian")
                        .setMessage("${it.failureModel?.msgShow}")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()

                                gsa_btn_rescan.visibility = View.GONE
                                gsa_cv.visibility = View.GONE

                                startScanner()
                            }
                        })
                        .showDialog()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        startScanner()
    }

    override fun handleResult(rawResult: Result?) {
        guestScanViewModel.getGuestById(rawResult?.text)
    }


}