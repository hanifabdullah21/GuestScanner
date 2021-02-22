package com.hanifabdullah.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.data.model.app.UserModel

class SharedPrefManager(context: Context) {

    companion object {
        private const val PREF_FILE_NAME = "guest_scanner_preferences"
        private const val PREF_KEY_IS_LOGGED_ID = "PREF_IS_LOGGED_IN"
        private const val PREF_KEY_BRIDES_ID = "PREF_BRIDES_ID"
        private const val PREF_KEY_ID_GUEST = "PREF_ID_GUEST"
        private const val PREF_KEY_NAME_GUEST = "PREF_NAME_GUEST"
        private const val PREF_KEY_FROM_GUEST = "PREF_FROM_GUEST"
        private const val PREF_KEY_QRCODE_GUEST = "PREF_QRCODE_GUEST"
        private const val PREF_KEY_IS_ADMIN = "PREF_IS_ADMIN"
    }

    private val preference: SharedPreferences =
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = preference.getBoolean(PREF_KEY_IS_LOGGED_ID, false)
        set(value) = preference.edit { putBoolean(PREF_KEY_IS_LOGGED_ID, value) }

    var isAdmin: Boolean
        get() = preference.getBoolean(PREF_KEY_IS_ADMIN, false)
        set(value) = preference.edit { putBoolean(PREF_KEY_IS_ADMIN, value) }

    var idGuest: Long?
        get() = preference.getLong(PREF_KEY_ID_GUEST, 0L)
        set(value) = preference.edit { putLong(PREF_KEY_ID_GUEST, value ?: 0) }

    var nameGuest: String?
        get() = preference.getString(PREF_KEY_NAME_GUEST, null)
        set(value) = preference.edit { putString(PREF_KEY_NAME_GUEST, value) }

    var fromGuest: String?
        get() = preference.getString(PREF_KEY_FROM_GUEST, null)
        set(value) = preference.edit { putString(PREF_KEY_FROM_GUEST, value) }

    var qrcodeGuest: String?
        get() = preference.getString(PREF_KEY_QRCODE_GUEST, null)
        set(value) = preference.edit { putString(PREF_KEY_QRCODE_GUEST, value) }

    var idBrides: Long?
        get() = preference.getLong(PREF_KEY_BRIDES_ID, 0L)
        set(value) = preference.edit { putLong(PREF_KEY_BRIDES_ID, value ?: 0) }

    fun userLoggedIn(guest: GuestModel?){
        isLoggedIn = true
        isAdmin = false
        idGuest = guest?.id
        nameGuest = guest?.name
        fromGuest = guest?.from
        qrcodeGuest = guest?.qrcode
        idBrides = guest?.bridesId
    }

    fun adminLoggedIn(userModel: UserModel?){
        isLoggedIn = true
        isAdmin = true
        idGuest = userModel?.id
        nameGuest = userModel?.username
        idBrides = userModel?.bridesId
    }

    fun userLoggedOut(){
        isLoggedIn = false
        idGuest = null
        nameGuest = null
        fromGuest = null
        qrcodeGuest = null
        idBrides = null
    }

}