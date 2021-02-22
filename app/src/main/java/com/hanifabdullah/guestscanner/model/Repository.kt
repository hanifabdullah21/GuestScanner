package com.hanifabdullah.guestscanner.model

import com.hanifabdullah.data.local.SharedPrefManager
import com.hanifabdullah.data.repository.RemoteRepository

data class Repository(
    val pref: SharedPrefManager? = null,
    val remote: RemoteRepository? = null
)