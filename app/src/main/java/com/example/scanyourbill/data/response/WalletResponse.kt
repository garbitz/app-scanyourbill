package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class WalletResponse(

	@field:SerializedName("data")
	val data: WalletData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class WalletResponseAll(
	@field:SerializedName("data")
	val data: List<WalletData?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class WalletData(

	@field:SerializedName("iconId")
	val iconId: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("walletId")
	val walletId: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("walletName")
	val walletName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
