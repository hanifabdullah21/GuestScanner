package com.hanifabdullah.data.repository

import android.content.Context
import com.hanifabdullah.data.helper.OnAuthorizationFailed
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.helper.requestBasic
import com.hanifabdullah.data.local.SharedPrefManager
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.remote.RetrofitConfig
import retrofit2.Response

class RemoteRepository (val context: Context, val authFailed: OnAuthorizationFailed): ResponseListener{

    var prefManager = SharedPrefManager(context)
    var apiService = RetrofitConfig.apiService
    lateinit var callback: ResponseListener

    fun getGuestById(id: String?, listener: ResponseListener){
        callback = listener
        val service = apiService.getGuestById("guestById", id)
        requestBasic(service, this)
    }

    fun getHome(bridesId: String?, listener: ResponseListener){
        callback = listener
        val service = apiService.getHome("home", bridesId)
        requestBasic(service, this)
    }

    fun login(username: String?, password: String?, listener: ResponseListener){
        callback = listener
        val service = apiService.login("login", username, password)
        requestBasic(service, this)
    }

    fun getAllGuest(bridesId: String?, listener: ResponseListener){
        callback = listener
        val service = apiService.getAllGuest("allGuest", bridesId)
        requestBasic(service, this)
    }

    fun verification(id: String?, listener: ResponseListener){
        callback = listener
        val service = apiService.verification("verification", id)
        requestBasic(service, this)
    }

    override fun <T> success(data: T) {
        callback.success(data)
    }

    override fun failure(failureModel: FailureModel) {
        if (failureModel.code == 401){
            authFailed.onAuthorizationFailed()
        }else{
            callback.failure(failureModel)
        }
    }

}