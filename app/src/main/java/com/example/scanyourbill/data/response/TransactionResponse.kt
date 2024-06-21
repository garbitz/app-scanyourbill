package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("data")
	val data: List<DataItemTransaction?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemTransaction(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("income")
	val income: Int? = null,

	@field:SerializedName("activities")
	val activities: List<ActivitiesItem?>? = null,

	@field:SerializedName("rangeSummary")
	val rangeSummary: Int? = null,

	@field:SerializedName("outcome")
	val outcome: Int? = null
)

data class ActivitiesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("activityType")
	val activityType: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
