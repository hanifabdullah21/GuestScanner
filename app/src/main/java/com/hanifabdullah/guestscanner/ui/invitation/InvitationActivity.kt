package com.hanifabdullah.guestscanner.ui.invitation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hanifabdullah.data.BASE_URL_IMAGE
import com.hanifabdullah.data.model.StatusResponse
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.data.util.SDF_TYPE7
import com.hanifabdullah.data.util.SDF_UTC
import com.hanifabdullah.data.util.formatDateCustomToCustom
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import com.hanifabdullah.guestscanner.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_invitation.*


class InvitationActivity : BaseActivity() {

    lateinit var viewModelFactory: InvitationViewModelFactory
    lateinit var viewModel: InvitationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation)

        viewModelFactory = InvitationViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(InvitationViewModel::class.java)

        inv_tv_name_guest.text = "To: "+viewModel.guestModel?.name
        inv_tv_from_guest.text = "From: "+viewModel.guestModel?.from
        Glide.with(this).load(BASE_URL_IMAGE + "${viewModel.guestModel?.qrcode}")
            .into(inv_iv_qrcode)

        setupObserver()
        viewModel.getHome()

        dash_iv_back.setOnClickListener {
            DialogMessage(this)
                .setTitle("Perhatian")
                .setMessage("Apakah anda yakin ingin logout?")
                .setTextButtonPrimary("Ya")
                .setTextButtonSecondary("Batal")
                .setListenerButtonPrimary(object : DialogMessageListener{
                    override fun onClick(dialogMessage: DialogMessage) {
                        dialogMessage.dismiss()
                        viewModel.logout()

                        finish()
                        val intent = Intent(this@InvitationActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }).showDialog()
        }


    }

    private fun setupObserver() {
        viewModel.home.observe(this, Observer {
            when (it.statusRequest) {
                StatusResponse.LOADING -> loading.show()
                StatusResponse.SUCCESS -> {
                    loading.dismiss()

                    inv_tv_name_man.text = it.data?.bridesModel?.man
                    inv_tv_parent_man.text =
                        "Putra dari Bapak " + it.data?.bridesModel?.fatherMan + " dan Ibu " + it.data?.bridesModel?.motherMan
                    inv_tv_name_women.text = it.data?.bridesModel?.women
                    inv_tv_parent_women.text =
                        "Putra dari Bapak " + it.data?.bridesModel?.fatherWomen + " dan Ibu " + it.data?.bridesModel?.motherWomen

                    val dateAkad = formatDateCustomToCustom(it.data?.scheduleModel?.dateAkad ?: "", SDF_UTC, SDF_TYPE7)
                    inv_tv_date_akad.text = dateAkad
                    inv_tv_time_akad.text = "Pukul " + it.data?.scheduleModel?.timeAkad
                    inv_tv_place_akad.text = it.data?.scheduleModel?.locationAkad
                    inv_tv_address_akad.text = it.data?.scheduleModel?.addressAkad
                    val latlongAkad = it.data?.scheduleModel?.mapAkad?.split(",")
                    inv_tv_maps_akad.setOnClickListener {
                        val locationUri =
                            Uri.parse(
                                "google.navigation:q=${latlongAkad?.get(0)},${latlongAkad?.get(
                                    1
                                )}"
                            )
                        val intent = Intent(Intent.ACTION_VIEW, locationUri)
                        startActivity(intent)
                    }

                    val dateResepsi = formatDateCustomToCustom(it.data?.scheduleModel?.date ?: "", SDF_UTC, SDF_TYPE7)
                    inv_tv_date_resepsi.text = dateResepsi
                    inv_tv_time_resepsi.text = "Pukul " + it.data?.scheduleModel?.time
                    inv_tv_place_resepsi.text = it.data?.scheduleModel?.location
                    inv_tv_address_resepsi.text = it.data?.scheduleModel?.address
                    val latlongResepsi = it.data?.scheduleModel?.map?.split(",")
                    inv_tv_maps_resepsi.setOnClickListener {
                        val locationUri =
                            Uri.parse(
                                "google.navigation:q=${latlongResepsi?.get(0)},${latlongResepsi?.get(
                                    1
                                )}"
                            )
                        val intent = Intent(Intent.ACTION_VIEW, locationUri)
                        startActivity(intent)
                    }

                    inv_tv_brides.text = it.data?.bridesModel?.man+" & "+it.data?.bridesModel?.women
                }
                StatusResponse.ERROR -> {
                    loading.dismiss()
                    DialogMessage(this)
                        .setTitle("Ups...")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                viewModel?.getHome()
                            }
                        })
                        .setDialogCancelable(false)
                        .showDialog()
                }
            }
        })
    }
}