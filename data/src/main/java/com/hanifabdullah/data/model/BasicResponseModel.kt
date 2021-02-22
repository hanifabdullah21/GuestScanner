package com.hanifabdullah.data.model

import com.google.gson.annotations.SerializedName

data class BasicResponseModel<out T>(

    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("result")
    val result: T? = null
)