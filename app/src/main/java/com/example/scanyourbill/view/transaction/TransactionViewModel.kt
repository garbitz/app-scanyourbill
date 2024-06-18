package com.example.scanyourbill.view.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.response.HomeResponse
import com.example.scanyourbill.data.response.TransactionResponse
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    val selectedCategory = MutableLiveData<String>()

}