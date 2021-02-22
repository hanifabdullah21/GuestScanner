package com.hanifabdullah.data.model.app

import com.google.gson.annotations.SerializedName

data class HomeModel(
    @field:SerializedName("brides")
    val bridesModel: BridesModel? = null,

    @field:SerializedName("schedule")
    val scheduleModel: ScheduleModel? = null
)