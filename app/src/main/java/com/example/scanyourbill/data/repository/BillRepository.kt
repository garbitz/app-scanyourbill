package com.example.scanyourbill.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.SaveBillRequest
import com.example.scanyourbill.data.UploadImageRequest
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.data.response.CreateBillResponse
import com.example.scanyourbill.data.response.WalletResponse
import com.example.scanyourbill.data.response.WalletResponseAll
import com.example.scanyourbill.view.scanbill.ImageUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class BillRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun uploadBillImage(uri: Uri, context: Context): BillResponse {
        val base64Image = ImageUtils.uriToBase64(context, uri)
        val request = UploadImageRequest(images = base64Image)
        return apiService.uploadBillImage(request)
    }

    suspend fun saveBill(saveBillRequest: SaveBillRequest): CreateBillResponse {
        return apiService.saveBill(saveBillRequest)
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