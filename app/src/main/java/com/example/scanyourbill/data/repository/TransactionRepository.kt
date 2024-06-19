package com.example.scanyourbill.data.repository

import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.CreateTransactionResponse
import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.TransactionResponse
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll
import retrofit2.http.Field

class TransactionRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun getHome(date: String): HomeResponse {
        return apiService.getHome(date)
    }

    suspend fun getTransactions(startDate: String, endDate: String, byCategory: Boolean, type: String): TransactionResponse {
        return apiService.getTransactions(startDate, endDate, byCategory, type)
    }

    suspend fun getListWallet(): WalletResponseAll{
        return apiService.getWallet()
    }

    suspend fun createTransaction(
        activityType: String, category: String, amount: Int, notes: String,
        date: String, walletId: String, iconId: Int, billId: Int
    ): CreateTransactionResponse {
        return apiService.createTransaction(activityType, category, amount, notes, date, walletId, iconId, billId)
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