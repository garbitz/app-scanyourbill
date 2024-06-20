package com.example.scanyourbill.view.scanbill

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.SaveBillRequest
import com.example.scanyourbill.data.repository.BillRepository
import com.example.scanyourbill.data.response.BillItem
import com.example.scanyourbill.data.response.BillResponse
import com.example.scanyourbill.data.response.ScannedItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class BillViewModel(private val billRepository: BillRepository) : ViewModel() {

    private val _billResponse = MutableLiveData<BillResponse>()
    val billResponse: LiveData<BillResponse> get() = _billResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun uploadBillImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val response = billRepository.uploadBillImage(uri, context)
                _billResponse.postValue(response)
            } catch (e: Exception) {
                Log.e("Upload Error", "uploadImage: ", e)
                _error.postValue("Failed to upload bill image: ${e.message}")
            }
        }
    }

    private fun BillItem?.toScannedItemsItem(): ScannedItemsItem? {
        return this?.let {
            ScannedItemsItem(
                items = listOf(this)
            )
        }
    }

    fun updateBillResponse(updatedActivities: List<BillItem?>) {
        val currentResponse = _billResponse.value ?: return

        val updatedScannedItems = currentResponse.data?.scannedItems?.map { scannedItem ->
            val updatedItems = updatedActivities.mapNotNull { updatedItem ->
                if (scannedItem!!.items?.any { it!!.title == updatedItem?.title } == true) {
                    updatedItem
                } else {
                    null
                }
            }

            val remainingItems = scannedItem!!.items?.filterNot { updatedItem ->
                updatedActivities.any { it?.title == updatedItem!!.title }
            }

            val updatedItemsList = updatedItems + remainingItems.orEmpty()

            scannedItem.copy(items = updatedItemsList)
        } ?: emptyList()

        val updatedResponse = currentResponse.copy(
            data = currentResponse.data?.copy(
                scannedItems = updatedScannedItems
            )
        )

        _billResponse.postValue(updatedResponse)
    }

    fun saveBill(saveBillRequest: SaveBillRequest) {
        viewModelScope.launch {
            try {
                val response = billRepository.saveBill(saveBillRequest)
                // Handle the response as needed
            } catch (e: Exception) {
                // Handle the exception
                _error.postValue("Failed to save bill: ${e.message}")
            }
        }
    }

}