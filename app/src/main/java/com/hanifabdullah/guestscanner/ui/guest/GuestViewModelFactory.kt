package com.hanifabdullah.guestscanner.ui.guest

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.guestscanner.model.Repository

class GuestViewModelFactory(
    val app: Application,
    val repo: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestViewModel::class.java)) {
            return GuestViewModel(
                app, repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}