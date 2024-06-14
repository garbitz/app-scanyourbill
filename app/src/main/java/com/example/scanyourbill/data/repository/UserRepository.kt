package com.example.scanyourbill.data.repository

import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserModel
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.LoginResponse
import com.example.scanyourbill.data.response.SignupResponse
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun login(username: String, pin: String): LoginResponse {
        return apiService.login(username, pin)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun register(username: String, pin: String): SignupResponse {
        return apiService.register(username, pin)
    }

    suspend fun logout() {
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}