package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddFeederResponse(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String
)
