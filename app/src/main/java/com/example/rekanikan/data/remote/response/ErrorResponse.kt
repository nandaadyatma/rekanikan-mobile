package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("errors")
	val errors: String,
)
