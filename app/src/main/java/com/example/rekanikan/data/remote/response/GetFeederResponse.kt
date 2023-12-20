package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetFeederResponse(

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
)
