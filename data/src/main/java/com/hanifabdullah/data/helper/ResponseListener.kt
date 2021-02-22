package com.hanifabdullah.data.helper

import com.hanifabdullah.data.model.FailureModel

interface ResponseListener {
    fun <T> success(data: T)
    fun failure(failureModel: FailureModel)
}