package com.hanifabdullah.guestscanner.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.guestscanner.model.Repository

class LoginViewModelFactory(
    val app: Application,
    val repo: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                app, repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}