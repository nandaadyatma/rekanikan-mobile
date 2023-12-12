package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanFishResponse(

	@field:SerializedName("Cara perawatan")
	val caraPerawatan: String,

	@field:SerializedName("Nama ikan ")
	val namaIkan: String
)
