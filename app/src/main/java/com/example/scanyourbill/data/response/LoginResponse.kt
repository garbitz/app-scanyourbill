package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("code")
	val code: Int? = null,

    @field:SerializedName("data")
	val data: User? = null,

    @field:SerializedName("tokens")
	val tokens: Tokens? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Boolean? = null
)

data class Refresh(

	@field:SerializedName("expires")
	val expires: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Access(

	@field:SerializedName("expires")
	val expires: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Tokens(

    @field:SerializedName("access")
	val access: Access? = null,

    @field:SerializedName("refresh")
	val refresh: Refresh? = null
)

data class User(

	@field:SerializedName("phoneNumber")
	val phoneNumber: Any? = null,

	@field:SerializedName("name")
	val name: Any? = null,

	@field:SerializedName("isPremium")
	val isPremium: Boolean? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)
