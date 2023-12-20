package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class EditSchedulesResponse(

	@field:SerializedName("data")
	val data: EditScheduleData
)

data class EditScheduleData(

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("hour")
	val hour: Int,

	@field:SerializedName("feeder_id")
	val feederId: String,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("minute")
	val minute: Int
)
