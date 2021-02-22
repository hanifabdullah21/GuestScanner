package com.hanifabdullah.data.helper

import com.hanifabdullah.data.model.BasicResponseModel
import com.hanifabdullah.data.model.FailureModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

val msgError =
    "Tidak bisa menghubungi server. Periksa Koneksi Internet Anda dan Cobalah Sesaat Lagi"

/**
 * Gunakan fungsi ini jika response permintaanmu langsung dalam bentuk data (isinya)
 *
 * @param observable is your request with the response
 * @param listener is your listener to give an action to all response
 * */
fun <T> request(observable: Observable<T>, listener: ResponseListener) {
    CompositeDisposable().add(
        observable
            .compose(SchedulerProvider().ioToMainObservableScheduler())
            .subscribe({
                listener.success(it)
            }, {
                onRequestFailure(it, listener)
            })
    )
}

/**
 * Gunakan fungsi ini jika response permintaanmu dibungkus dalam json objek status dan data
 *
 * @param observable is your request with the response
 * @param listener is your listener to give an action to all response
 * */
fun <T> requestBasic(observable: Observable<BasicResponseModel<T>>, listener: ResponseListener) {
    CompositeDisposable().add(
        observable
            .compose(SchedulerProvider().ioToMainObservableScheduler())
            .subscribe({
                if (it.success == false) {
                    listener.failure(
                        FailureModel(
                            code = 400,
                            msgShow = it.message,
                            msgSystem = it.message
                        )
                    )
                } else {
                    listener.success(it.result)
                }
            }, {
                onRequestFailure(it, listener)
            })
    )
}

/**
 * Fungsi ini digunakan untuk menyederhanakan permintaan yang gagal agar dapat digunakan pada kedua
 * fungsi request diatas
 *
 * @param throwable is the throwable failure
 * @param listener is the listener of request
 * */
fun onRequestFailure(throwable: Throwable, listener: ResponseListener) {
    when (throwable) {
        is HttpException -> {
            var failureModel = FailureModel()
            try {
                val body = throwable.response()?.errorBody()
                val json = body?.string()
                if (json != null) {
                    FailureModel(
                        throwable.response()?.code(),
                        msgError,
                        "Try Success : " + throwable.message()
                    )
                }
            } catch (e: Exception) {
                failureModel = FailureModel(402, msgError, "Exception : " + e.message)
            }
            listener.failure(failureModel)
        }
        else -> {
            listener.failure(
                FailureModel(403, msgError, "Not HTTP Exception : " + throwable.message)
            )
        }
    }
}