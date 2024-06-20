package com.example.scanyourbill.data.response

import com.google.gson.annotations.SerializedName

data class BillResponse(

	@field:SerializedName("data")
	val data: BillData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ScannedItemsItem(

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("items")
	val items: List<BillItem?>? = null
)

data class BillDetails(

	@field:SerializedName("billName")
	val billName: String? = null,

	@field:SerializedName("serviceCharge")
	val serviceCharge: Int? = null,

	@field:SerializedName("grandTotal")
	val grandTotal: Int? = null,

	@field:SerializedName("discount")
	val discount: Int? = null,

	@field:SerializedName("tax")
	val tax: Int? = null,

	@field:SerializedName("others")
	val others: Int? = null
)

data class BillItem(

	@field:SerializedName("price")
	var price: Int? = null,

	@field:SerializedName("title")
    var title: String? = null
)

data class BillData(

	@field:SerializedName("scannedItems")
	val scannedItems: List<ScannedItemsItem?>? = null,

	@field:SerializedName("billDetails")
	val billDetails: BillDetails? = null
)

fun BillItem.toPair(): Pair<String, Int> {
	return (title.toString() to price ?: 0) as Pair<String, Int>
}
fun ScannedItemsItem.toItemsMap(): Map<String, Int> {
	return items?.associate { it!!.toPair() } ?: emptyMap()
}
