package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
