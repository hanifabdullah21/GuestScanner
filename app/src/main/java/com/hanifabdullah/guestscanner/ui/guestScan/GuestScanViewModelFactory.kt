package com.hanifabdullah.guestscanner.ui.guestScan

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.guestscanner.model.Repository

class GuestScanViewModelFactory(
    val app: Application,
    val repo: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestScanViewModel::class.java)) {
            return GuestScanViewModel(
                app, repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}