package com.hanifabdullah.guestscanner.ui.invitation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.model.StatusResponseModel
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.data.model.app.HomeModel
import com.hanifabdullah.guestscanner.model.Repository

class InvitationViewModel (val app: Application, val repo: Repository): AndroidViewModel(app){

    val guestModel: GuestModel?
        get() {
            return GuestModel(
                id = repo.pref?.idGuest,
                name = repo.pref?.nameGuest,
                from = repo.pref?.fromGuest,
                qrcode = repo.pref?.qrcodeGuest,
                bridesId = repo.pref?.idBrides
            )
        }

    val home = MutableLiveData<StatusResponseModel<HomeModel>>()

    fun getHome(){
        home.postValue(StatusResponseModel.loading())
        repo.remote?.getHome(guestModel?.bridesId.toString(), object : ResponseListener{
            override fun <T> success(data: T) {
                home.postValue(StatusResponseModel.success(data as HomeModel))
            }

            override fun failure(failureModel: FailureModel) {
                home.postValue(StatusResponseModel.error(failureModel))
            }

        })
    }

    fun logout(){
        repo.pref?.userLoggedOut()
    }

}