package com.hanifabdullah.data.model.app

import com.google.gson.annotations.SerializedName

data class GuestModel(

	@field:SerializedName("brides_id")
	val bridesId: Long? = null,

	@field:SerializedName("isAttended")
	val isAttended: Int? = null,

	@field:SerializedName("public")
	val jsonMemberPublic: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("qrcode")
	val qrcode: String? = null,

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("id")
	val id: Long? = null
)
