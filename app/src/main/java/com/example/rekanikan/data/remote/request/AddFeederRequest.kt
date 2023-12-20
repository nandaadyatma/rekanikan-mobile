package com.example.rekanikan.data.remote.request

import com.google.gson.annotations.SerializedName

data class AddFeederRequest(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)
