package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(

	@field:SerializedName("data")
	val data: DataSignup? = null,

	@field:SerializedName("tokens")
	val tokens: Tokens? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataSignup(

	@field:SerializedName("pin")
	val pin: String? = null,

	@field:SerializedName("isPremium")
	val isPremium: Boolean? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)


