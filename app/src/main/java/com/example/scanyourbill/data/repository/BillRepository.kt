package com.example.scanyourbill.data.repository

import android.content.Context
import android.net.Uri
import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll
import com.example.scanyourbill.view.scanbill.ImageUtils
import okhttp3.MultipartBody

class BillRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun uploadBillImage(uri: Uri, context: Context): BillResponse {
        val base64Image = ImageUtils.uriToBase64(context, uri)
        return apiService.uploadBillImage(base64Image)
    }


    companion object {
        @Volatile
        private var instance: BillRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): BillRepository =
            instance ?: synchronized(this) {
                instance ?: BillRepository(userPreference, apiService)
            }.also { instance = it }
    }
}