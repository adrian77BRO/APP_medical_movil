package com.example.pushnotificationsapp.register.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.register.data.models.RegisterState
import com.example.pushnotificationsapp.register.data.repository.RegisterRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: RegisterRepository = RegisterRepository()
) : ViewModel() {

    private val _fname = MutableLiveData("")
    val fname: LiveData<String> = _fname

    private val _lname = MutableLiveData("")
    val lname: LiveData<String> = _lname

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _deviceToken = MutableLiveData("")
    val deviceToken: LiveData<String> = _deviceToken

    private val _registerState = MutableLiveData<RegisterState>(RegisterState.Idle)
    val registerState: LiveData<RegisterState> = _registerState

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("FCM", "Fetching FCM token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            _deviceToken.postValue(token)
            Log.d("FCM", "FCM Token: $token")
        }
    }

    fun onFnameChange(newFname: String) {
        _fname.value = newFname
    }

    fun onLnameChange(newLname: String) {
        _lname.value = newLname
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun register() {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = repository.registerUser(
                    fname.value ?: "",
                    lname.value ?: "",
                    email.value ?: "",
                    password.value ?: "",
                    deviceToken.value ?: ""
                )
                if (response.status == "success") {
                    _registerState.value = RegisterState.Success(response.message)
                } else {
                    _registerState.value = RegisterState.Error(response.message)
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}