package com.example.scanyourbill.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.response.HomeResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TransactionRepository): ViewModel() {

    private val _homeResult = MutableLiveData<HomeResponse>()
    val homeResult: LiveData<HomeResponse> = _homeResult

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHome(date: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val response = repository.getHome(date)
                _homeResult.postValue(response)
            } catch (e: Exception) {
                _homeResult.postValue(HomeResponse(message = e.message))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}