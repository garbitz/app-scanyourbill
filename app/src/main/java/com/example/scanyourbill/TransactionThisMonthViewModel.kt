package com.example.scanyourbill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.response.TransactionResponse
import kotlinx.coroutines.launch

class TransactionThisMonthViewModel(private val repository: TransactionRepository): ViewModel() {

    private val _result = MutableLiveData<TransactionResponse>()
    val result: LiveData<TransactionResponse> = _result

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _sum = MutableLiveData<Map<String, Int>>()
    val sum: LiveData<Map<String, Int>> = _sum


    fun getTransactions(startDate: String, endDate: String, byCategory: Boolean, type: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.getTransactions(startDate, endDate, byCategory, type)
                Log.d("TransactionViewModel", "Fetched transactions: ${response.data}")
                getSum(response)
                _result.postValue(response)
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error fetching transactions", e)
                _result.postValue(TransactionResponse(message = e.message))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun getSum(response: TransactionResponse) {
        var sum = 0
        var inflow = 0
        var outflow = 0
        response.data?.forEach {
            sum += it?.rangeSummary!!
            inflow += it?.income!!
            outflow += it?.outcome!!
        }
        Log.d("TransactionViewModel", "Sum: $sum")
        _sum.postValue(mapOf("sum" to sum, "inflow" to inflow, "outflow" to outflow))
    }
}