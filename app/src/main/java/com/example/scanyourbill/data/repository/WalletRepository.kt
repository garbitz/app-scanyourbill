package com.example.scanyourbill.data.repository

import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll

class WalletRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun getWallet(): WalletResponseAll {
        return apiService.getWallet()
    }

    suspend fun createWallet(walletName: String, amount: Int, note: String, iconId: Int): WalletResponse {
        return apiService.createWallet(walletName, amount, note, iconId)
    }

    companion object {
        @Volatile
        private var instance: WalletRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): WalletRepository =
            instance ?: synchronized(this) {
                instance ?: WalletRepository(userPreference, apiService)
            }.also { instance = it }
    }
}