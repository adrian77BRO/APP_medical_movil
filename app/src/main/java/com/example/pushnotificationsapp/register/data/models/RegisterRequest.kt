package com.example.pushnotificationsapp.register.data.models

data class RegisterRequest(
    val fname: String,
    val lname: String,
    val email: String,
    val password: String,
    val device_token: String
)