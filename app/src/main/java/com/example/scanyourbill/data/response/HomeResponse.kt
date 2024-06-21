package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TopActivitiesItem(

	@field:SerializedName("walletId")
	val walletId: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("activityType")
	val activityType: Boolean? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class Data(

	@field:SerializedName("income")
	val income: Int? = null,

	@field:SerializedName("monthSummary")
	val monthSummary: Int? = null,

	@field:SerializedName("topActivities")
	val topActivities: List<TopActivitiesItem?>? = null,

	@field:SerializedName("topUsedWallets")
	val topUsedWallets: List<TopUsedWalletsItem?>? = null,

	@field:SerializedName("outcome")
	val outcome: Int? = null
)

data class TopUsedWalletsItem(

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
