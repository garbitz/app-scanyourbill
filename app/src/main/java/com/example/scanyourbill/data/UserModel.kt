package com.example.scanyourbill.data

data class UserModel(
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)
