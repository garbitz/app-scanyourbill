package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

	@field:SerializedName("data")
	val data: Transaction? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Transaction(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("iconId")
	val iconId: String? = null,

	@field:SerializedName("walletId")
	val walletId: String? = null,

	@field:SerializedName("activityId")
	val activityId: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("billId")
	val billId: String? = null,

	@field:SerializedName("activityType")
	val activityType: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
