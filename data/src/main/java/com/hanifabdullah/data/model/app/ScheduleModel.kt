package com.hanifabdullah.data.model.app

import com.google.gson.annotations.SerializedName

data class ScheduleModel(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("address_akad")
	val addressAkad: String? = null,

	@field:SerializedName("brides_id")
	val bridesId: Int? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("map_akad")
	val mapAkad: String? = null,

	@field:SerializedName("location_akad")
	val locationAkad: String? = null,

	@field:SerializedName("time_akad")
	val timeAkad: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("date_akad")
	val dateAkad: String? = null,

	@field:SerializedName("map")
	val map: String? = null
)
