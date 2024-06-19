package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class CreateBillResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
