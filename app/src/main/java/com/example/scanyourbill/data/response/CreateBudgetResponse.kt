package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class CreateBudgetResponse(

	@field:SerializedName("data")
	val data: DataBudget? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataBudget(

	@field:SerializedName("walletId")
	val walletId: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("budgetId")
	val budgetId: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("spendingAmount")
	val spendingAmount: Int? = null
)
