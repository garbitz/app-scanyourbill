package com.example.scanyourbill.view.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.TransactionResponse
import com.example.scanyourbill.data.response.WalletData
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    val selectedCategory = MutableLiveData<String>()

    val selectedDate = MutableLiveData<String>()

    val activityType = MutableLiveData<String>()

    val walletList = MutableLiveData<List<WalletData>>()

    init {
        fetchWalletList()
    }

    private fun fetchWalletList() {
        viewModelScope.launch {
            try {
                val response = repository.getListWallet()
                walletList.value = (response.data ?: emptyList()) as List<WalletData>?
            } catch (e: Exception) {
                // Handle error
                walletList.value = emptyList()
            }
        }
    }

    fun createTransaction(activityType: String, category: String, amount: Int, notes: String, date: String, walletId: String, iconId: Int, billId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.createTransaction(activityType, category, amount, notes, date, walletId, iconId, billId)
                // Handle success
                Log.d("TransactionViewModel", "Transaction created: ${response.data}")
            } catch (e: Exception) {
                // Handle error
                Log.e("TransactionViewModel", "Failed to create transaction", e)
            }
        }
    }
}