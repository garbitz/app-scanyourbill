package com.example.scanyourbill.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.scanyourbill.data.UserModel
import com.example.scanyourbill.data.repository.UserRepository
import com.example.scanyourbill.data.response.LoginResponse
import com.example.scanyourbill.data.response.SignupResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _registerResult = MutableLiveData<SignupResponse>()
    val registerResult: LiveData<SignupResponse> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading


    fun login(username: String, pin: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val response = repository.login(username, pin)
                if (response.status == true) {
                    _loginResult.postValue(response)
                    saveSession(UserModel(response.data?.username ?: "", response.tokens?.access?.token ?: ""))
                } else {
                    // Handle error based on response code and message
                    _loginResult.postValue(response)
                }
            } catch (e: Exception) {
                _loginResult.postValue(LoginResponse(message = e.message))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun register(username: String, pin: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val response = repository.register(username, pin)
                if (response.status == true) {
                    _registerResult.postValue(response)
                    saveUsername(username)
                    saveSession(UserModel(username, response.tokens?.access?.token ?: ""))
                } else {
                    // Handle error based on response code and message
                    _registerResult.postValue(response)
                }
            } catch (e: Exception) {
                _registerResult.postValue(SignupResponse(message = e.message))
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

    fun saveUsername(username: String) {
        viewModelScope.launch {
            repository.saveUsername(username)
        }
    }

    fun getUsername(): LiveData<String> {
        return repository.getUsername().asLiveData()

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