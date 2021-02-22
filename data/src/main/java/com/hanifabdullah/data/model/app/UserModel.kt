package com.hanifabdullah.data.model.app

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("brides_id")
	val bridesId: Long? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("username")
	val username: String? = null
)
