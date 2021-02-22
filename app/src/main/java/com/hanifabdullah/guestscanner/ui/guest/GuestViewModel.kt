package com.hanifabdullah.guestscanner.ui.guest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.model.StatusResponseModel
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.guestscanner.model.Repository

class GuestViewModel(val app: Application, val repository: Repository) : AndroidViewModel(app) {

    val guest = MutableLiveData<StatusResponseModel<ArrayList<GuestModel>>>()

    fun getAllGuest() {
        guest.postValue(StatusResponseModel.loading())
        repository.remote?.getAllGuest(
            repository.pref?.idBrides.toString(),
            object : ResponseListener {
                override fun <T> success(data: T) {
                    val list = data as ArrayList<GuestModel>
                    if (list.isNullOrEmpty()) {
                        guest.postValue(StatusResponseModel.empty())
                    } else {
                        guest.postValue(StatusResponseModel.success(list))
                    }
                }

                override fun failure(failureModel: FailureModel) {
                    guest.postValue(StatusResponseModel.error(failureModel))
                }

            })
    }

}