package com.example.scanyourbill.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.UserModel
import com.example.scanyourbill.data.repository.UserRepository
import com.example.scanyourbill.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading


    fun login(username: String, pin: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val response = repository.login(username, pin)
                _loginResult.postValue(response)
                saveSession(UserModel(response.data?.username ?: "", response.tokens?.access?.token ?: ""))
            } catch (e: Exception) {
                _loginResult.postValue(LoginResponse(message = e.message))

            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}