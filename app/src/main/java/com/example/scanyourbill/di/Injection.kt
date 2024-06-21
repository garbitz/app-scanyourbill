package com.example.scanyourbill.di

import android.content.Context
import com.example.scanyourbill.data.ApiConfig
import com.example.scanyourbill.data.TokenProvider
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.dataStore
import com.example.scanyourbill.data.repository.BillRepository
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.repository.UserRepository
import com.example.scanyourbill.data.repository.WalletRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(context, TokenProvider(pref))
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideTransactionRepository(context: Context): TransactionRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(context, TokenProvider(pref))
        return TransactionRepository.getInstance(pref, apiService)
    }

    fun provideWalletRepository(context: Context): WalletRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(context, TokenProvider(pref))
        return WalletRepository.getInstance(pref, apiService)
    }

    fun provideBillRepository(context: Context): BillRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(context, TokenProvider(pref))
        return BillRepository.getInstance(pref, apiService)
    }
}