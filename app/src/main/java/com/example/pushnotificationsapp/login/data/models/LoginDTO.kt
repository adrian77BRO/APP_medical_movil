package com.example.pushnotificationsapp.login.data.models

data class LoginDTO(
    val status: String,
    val message: String,
    val user: User?,
    val token: String?
)