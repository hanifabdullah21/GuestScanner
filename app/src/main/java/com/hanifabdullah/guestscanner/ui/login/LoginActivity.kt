package com.hanifabdullah.guestscanner.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.data.model.StatusResponse
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseActivity
import com.hanifabdullah.guestscanner.ui.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    lateinit var viewModelFactory: LoginViewModelFactory
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModelFactory = LoginViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setupObserver()

        login_btn.setOnClickListener {
            login_til_code.error = null
            login_til_password.error = null

            when{
                login_til_code.editText?.text.isNullOrBlank() || login_til_code.editText?.text.isNullOrEmpty() -> {
                    login_til_code.requestFocus()
                    login_til_code.error = "Tidak boleh kosong"
                    return@setOnClickListener
                }
                login_til_password.editText?.text.isNullOrBlank() || login_til_password.editText?.text.isNullOrEmpty() -> {
                    login_til_password.requestFocus()
                    login_til_password.error = "Tidak boleh kosong"
                    return@setOnClickListener
                }
                else -> {
                    viewModel.login(
                        login_til_code.editText?.text.toString(),
                        login_til_password.editText?.text.toString()
                    )
                }
            }
        }

        login_iv_digimedia.setOnClickListener {
            profilDigimedia()
        }
    }

    private fun setupObserver() {
        viewModel.user.observe(this, Observer {
            when(it.statusRequest){
                StatusResponse.LOADING -> {}
                StatusResponse.SUCCESS -> {
                    viewModel.successLogin(it.data)

                    finish()
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                StatusResponse.ERROR -> {
                    DialogMessage(this)
                        .setTitle("Perhatian")
                        .setMessage("${it.failureModel?.msgShow}")
                        .showDialog()
                }
            }
        })
    }
}