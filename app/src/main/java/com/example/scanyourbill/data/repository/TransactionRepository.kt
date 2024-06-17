package com.example.scanyourbill.data.repository

import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.HomeResponse

class TransactionRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun getHome(date: String): HomeResponse {
        return apiService.getHome(date)
    }



    companion object {
        @Volatile
        private var instance: TransactionRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): TransactionRepository =
            instance ?: synchronized(this) {
                instance ?: TransactionRepository(userPreference, apiService)
            }.also { instance = it }
    }


}