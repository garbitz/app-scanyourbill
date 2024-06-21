package com.example.scanyourbill.view.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.WalletRepository
import com.example.scanyourbill.data.response.WalletData
import com.example.scanyourbill.data.response.WalletResponse
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: WalletRepository) : ViewModel() {
    private val _wallets = MutableLiveData<List<WalletData>>()
    val wallets: LiveData<List<WalletData>> get() = _wallets


    private val _createWalletResult = MutableLiveData<WalletResponse>()
    val createWalletResult: LiveData<WalletResponse> get() = _createWalletResult

    fun fetchWallets() {
        viewModelScope.launch {
            try {
                val response = repository.getWallet()
                _wallets.postValue((response.data ?: listOf()) as List<WalletData>?)
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }

    fun createWallet(walletName: String, amount: Int, note: String, iconId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.createWallet(walletName, amount, note, iconId)
                _createWalletResult.postValue(response)
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}