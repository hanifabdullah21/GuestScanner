package com.hanifabdullah.data.model.app

import com.google.gson.annotations.SerializedName

data class BridesModel(

	@field:SerializedName("mother_man")
	val motherMan: String? = null,

	@field:SerializedName("father_women")
	val fatherWomen: String? = null,

	@field:SerializedName("father_man")
	val fatherMan: String? = null,

	@field:SerializedName("mother_women")
	val motherWomen: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("man")
	val man: String? = null,

	@field:SerializedName("women")
	val women: String? = null
)
