package com.example.pushnotificationsapp.login.data.repository

import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.login.data.models.LoginDTO
import com.example.pushnotificationsapp.login.data.models.LoginRequest
import org.json.JSONObject
import retrofit2.Response

class LoginRepository {
    suspend fun loginUser(email: String, password: String): LoginDTO {
        val request = LoginRequest(email, password)
        val response: Response<LoginDTO> = RetrofitHelper.loginService.login(request)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw Exception("Error: La respuesta está vacía")
        } else {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val jsonError = JSONObject(errorBody)
                val message = jsonError.optString("message", "Error desconocido")
                throw Exception(message)
            } else {
                throw Exception("Error desconocido")
            }
        }
    }
}