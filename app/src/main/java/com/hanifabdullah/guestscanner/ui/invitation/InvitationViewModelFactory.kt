package com.hanifabdullah.guestscanner.ui.invitation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanifabdullah.guestscanner.model.Repository

class InvitationViewModelFactory(
    val app: Application,
    val repo: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvitationViewModel::class.java)) {
            return InvitationViewModel(
                app, repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}