package com.hanifabdullah.guestscanner.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.model.StatusResponseModel
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.guestscanner.model.Repository

class MainViewModel (val app: Application, val repo: Repository): AndroidViewModel(app){

    val guest = MutableLiveData<StatusResponseModel<GuestModel>>()

    fun getGuestById(id: String?){
        guest.postValue(StatusResponseModel.loading())
        repo.remote?.getGuestById(id, object : ResponseListener{
            override fun <T> success(data: T) {
                guest.postValue(StatusResponseModel.success(data as GuestModel))
            }

            override fun failure(failureModel: FailureModel) {
                guest.postValue(StatusResponseModel.error(failureModel))
            }

        })
    }

    fun saveGuest(guestModel: GuestModel?){
        repo.pref?.userLoggedIn(guestModel)
    }

}