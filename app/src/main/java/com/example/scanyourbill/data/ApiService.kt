package com.example.scanyourbill.data

import com.example.scanyourbill.data.response.CreateTransactionResponse
import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.LoginResponse
import com.example.scanyourbill.data.response.SignupResponse
import com.example.scanyourbill.data.response.TransactionResponse
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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
}