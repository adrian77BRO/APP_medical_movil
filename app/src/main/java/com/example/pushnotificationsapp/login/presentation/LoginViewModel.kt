package com.example.pushnotificationsapp.login.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.core.storage.TokenManager
import com.example.pushnotificationsapp.login.data.models.LoginState
import com.example.pushnotificationsapp.login.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoginRepository()
    private val tokenManager = TokenManager(application)

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.loginUser(email.value ?: "", password.value ?: "")
                tokenManager.setToken(response.token ?: "")
                if (response.status == "success") {
                    _loginState.value = LoginState.Success(response.message)
                } else {
                    _loginState.value = LoginState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

/*import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pushnotificationsapp.login.data.models.LoginState
import com.example.pushnotificationsapp.login.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val loginRepository = LoginRepository(application)

    private val _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?> get() = _loginState

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = loginRepository.login(email, password)
            result.onSuccess { loginDTO ->
                val username = loginDTO.user?.username ?: "Usuario"
                val token = loginDTO.token ?: ""
                _loginState.value = LoginState.Success(username, token)
            }.onFailure { error ->
                _loginState.value = LoginState.Error(error.localizedMessage ?: "Error desconocido")
            }
        }
    }
}*/