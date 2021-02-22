package com.hanifabdullah.guestscanner.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifabdullah.data.helper.ResponseListener
import com.hanifabdullah.data.model.FailureModel
import com.hanifabdullah.data.model.StatusResponseModel
import com.hanifabdullah.data.model.app.UserModel
import com.hanifabdullah.guestscanner.model.Repository

class LoginViewModel(val app: Application, val repository: Repository) : AndroidViewModel(app) {

    val user = MutableLiveData<StatusResponseModel<UserModel>>()

    fun login(username: String?, password: String?) {
        user.postValue(StatusResponseModel.loading())
        repository.remote?.login(username, password, object : ResponseListener {
            override fun <T> success(data: T) {
                user.postValue(StatusResponseModel.success(data as UserModel))
            }

            override fun failure(failureModel: FailureModel) {
                user.postValue(StatusResponseModel.error(failureModel))
            }

        })
    }

    fun successLogin(userModel: UserModel?){
        repository.pref?.adminLoggedIn(userModel)
    }


}