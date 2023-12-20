package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetSchedulesResponse(

	@field:SerializedName("data")
	val data: List<DataScheduleItem>
)

data class DataScheduleItem(

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("hour")
	val hour: Int,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("minute")
	val minute: Int
)
