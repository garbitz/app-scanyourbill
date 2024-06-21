package com.example.scanyourbill.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.response.DataItem
import com.example.scanyourbill.data.response.ErrorResponse
import com.example.scanyourbill.data.response.SearchResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: TransactionRepository): ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> = _searchResult

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _selectedResult = MutableLiveData<DataItem>()
    val selectedResult: LiveData<DataItem> = _selectedResult

    fun search(notes: String, category: String, type: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val response = repository.search(notes, category, type)
                if (response.status == true) {
                    _searchResult.postValue(response)
                } else {
                    // Handle error based on response code and message
                    _searchResult.postValue(response)
                }
            } catch (e: Exception) {
                _searchResult.postValue(SearchResponse(message = e.message))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun selectResult(date: String, amount: Int, notes: String,  category: String) {
        viewModelScope.launch {
            _selectedResult.postValue(DataItem(date, amount, notes, category))

        }
    }


}