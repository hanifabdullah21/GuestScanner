package com.hanifabdullah.guestscanner.ui.guestScan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.model.StatusResponseModel
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.guestscanner.model.Repository

class GuestScanViewModel(val app: Application, val repository: Repository) : AndroidViewModel(app) {

    val guest = MutableLiveData<StatusResponseModel<GuestModel>>()
    val verification = MutableLiveData<StatusResponseModel<String?>>()

    val bridesId = repository.pref?.idBrides

    fun getGuestById(idGuest: String?) {
        guest.postValue(StatusResponseModel.loading())
        repository.remote?.getGuestById(idGuest, object : ResponseListener{
            override fun <T> success(data: T) {
                guest.postValue(StatusResponseModel.success(data as GuestModel))
            }

            override fun failure(failureModel: FailureModel) {
                guest.postValue(StatusResponseModel.error(failureModel))
            }
        })
    }

    fun verification(idGuest: String?) {
        verification.postValue(StatusResponseModel.loading())
        repository.remote?.verification(idGuest, object : ResponseListener{
            override fun <T> success(data: T) {
                verification.postValue(StatusResponseModel.success(data as String))
            }

            override fun failure(failureModel: FailureModel) {
                verification.postValue(StatusResponseModel.error(failureModel))
            }
        })
    }

}