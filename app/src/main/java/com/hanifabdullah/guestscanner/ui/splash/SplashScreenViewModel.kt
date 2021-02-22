package com.hanifabdullah.guestscanner.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hanifabdullah.guestscanner.model.Repository

class SplashScreenViewModel (val app: Application, val repo: Repository): AndroidViewModel(app){

    val isLoggedIn : Boolean?
        get() = repo.pref?.isLoggedIn

    val isAdmin : Boolean?
        get() = repo.pref?.isAdmin

}