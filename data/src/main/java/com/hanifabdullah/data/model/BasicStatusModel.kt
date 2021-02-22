package com.hanifabdullah.data.model

import com.google.gson.annotations.SerializedName

data class BasicStatusModel(
    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("code")
    val code: Int? = null
)