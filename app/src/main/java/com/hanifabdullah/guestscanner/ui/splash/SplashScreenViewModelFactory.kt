package com.hanifabdullah.guestscanner.ui.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.guestscanner.model.Repository

class SplashScreenViewModelFactory(
    val app: Application,
    val repo: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(
                app, repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}