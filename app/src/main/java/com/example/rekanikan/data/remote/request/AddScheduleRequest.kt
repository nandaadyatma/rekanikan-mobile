package com.example.rekanikan.data.remote.request

import com.google.gson.annotations.SerializedName

data class AddScheduleRequest(
    @SerializedName("hour") val hour: Int,
    @SerializedName("minute") val minute: Int,
    @SerializedName("portion") val portion: Int,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("feeder_id") val feederId: String
)
