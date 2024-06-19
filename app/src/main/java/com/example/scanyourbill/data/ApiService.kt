package com.example.scanyourbill.data

import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.data.response.CreateBillResponse
import com.example.scanyourbill.data.response.CreateTransactionResponse
import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.LoginResponse
import com.example.scanyourbill.data.response.SearchResponse
import com.example.scanyourbill.data.response.SignupResponse
import com.example.scanyourbill.data.response.TransactionResponse
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("pin") pin: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("userName") username: String,
        @Field("userPin") pin: String
    ): SignupResponse

    @GET("detail/home")
    suspend fun getHome(
        @Query("date") date: String
    ): HomeResponse

    @GET("wallet")
    suspend fun getWallet(): WalletResponseAll

    @FormUrlEncoded
    @POST("wallet")
    suspend fun createWallet(
        @Field("walletName") walletName: String,
        @Field("amount") amount: Int,
        @Field("note") note: String,
        @Field("accountId") accountId: Int
    ): WalletResponse

    @FormUrlEncoded
    @POST("detail/transaksi")
    suspend fun getTransactions(
        @Field("startDate") startDate: String,
        @Field("endDate") endDate: String,
        @Field("byCategory") byCategory: Boolean,
        @Field("type") type: String
    ): TransactionResponse

    @FormUrlEncoded
    @POST("activity")
    suspend fun createTransaction(
        @Field("activityType") activityType: String,
        @Field("category") category: String,
        @Field("amount") amount: Int,
        @Field("notes") notes: String,
        @Field("date") date: String,
        @Field("walletId") walletId: String,
        @Field("iconId") iconId: Int,
        @Field("billId") billId: Int
    ): CreateTransactionResponse

    @POST("bill/readImages")
    suspend fun uploadBillImage(
        @Body request: UploadImageRequest
    ): BillResponse

    @FormUrlEncoded
    @POST("detail/search")
    suspend fun search(
        @Field("key") notes: String,
        @Field("category") category: String,
        @Field("type") type: String
    ): SearchResponse

    @POST("bill/processBill")
    suspend fun saveBill(@Body saveBillRequest: SaveBillRequest): CreateBillResponse

}

data class UploadImageRequest(
    @SerializedName("images")
    val images: String
)

data class SaveBillRequest(
    @SerializedName("billId") val billId: String,
    @SerializedName("walletId") val walletId: String,
    @SerializedName("items") val items: Map<String, Map<String, Int>>,
    @SerializedName("billDetails") val billDetails: Map<String, Any?>
)