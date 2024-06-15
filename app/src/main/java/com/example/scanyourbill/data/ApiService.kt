package com.example.scanyourbill.data

import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.LoginResponse
import com.example.scanyourbill.data.response.SignupResponse
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
}