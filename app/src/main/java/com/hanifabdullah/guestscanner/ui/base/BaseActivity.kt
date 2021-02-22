package com.hanifabdullah.guestscanner.ui.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hanifabdullah.data.helper.OnAuthorizationFailed
import com.hanifabdullah.data.local.SharedPrefManager
import com.hanifabdullah.data.repository.RemoteRepository
import com.hanifabdullah.data.ui.DialogLoading
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.model.Repository
import com.hanifabdullah.guestscanner.ui.main.MainActivity


abstract class BaseActivity : AppCompatActivity() {

    lateinit var pref: SharedPrefManager
    lateinit var remoteRepository: RemoteRepository
    lateinit var repository: Repository

    lateinit var loading: DialogLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = SharedPrefManager(this)
        remoteRepository = RemoteRepository(this, object : OnAuthorizationFailed {
            override fun onAuthorizationFailed() {
                val intent = Intent(this@BaseActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        })
        repository = Repository(pref, remoteRepository)

        loading = DialogLoading(this)
    }

    fun profilDigimedia() {
        DialogMessage(this)
            .setImageDrawable(R.drawable.ic_digimedia)
            .setTitle("Profil Digimedia")
            .setMessage("Digimedia adalah perusahaan media yang menyediakan berbagai layanan untuk membantu event anda")
            .setTextButtonPrimary("Kunjungi Profil")
            .setTextButtonSecondary("Ok")
            .setListenerButtonSecondary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                }
            })
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()

                    try {
                        val url = "https://wa.me/6289605177985?text=Halo%20Rizky,%20saya%20mengetahui%20Digimedia%20dari%20Aplikasi%20Guest%20Scanner.%20Saya%20ingin%20mengetahui%20info%20lebih%20lanjut%20tentang%20pembuatan%20Aplikasi%20Android,%20Web%20atau%20Digital%20Marketing"
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    } catch (e: PackageManager.NameNotFoundException) {
                        openDialogProfil()
                    }

                }
            })
            .showDialog()

    }

    private fun openDialogProfil() {
        DialogMessage(this)
            .setImageDrawable(R.drawable.ic_digimedia)
            .setTitle("Profil Digimedia")
            .setMessage("Hubungi Rizky/+6289605177985 (Whatsapp) untuk mengetahui info lebih lanjut tentang Pembuatan Aplikasi Android, Web atau Digital Marketing")
            .showDialog()
    }

}