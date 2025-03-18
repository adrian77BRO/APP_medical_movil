package com.example.pushnotificationsapp.register.data.repository

import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.register.data.models.RegisterDTO
import com.example.pushnotificationsapp.register.data.models.RegisterRequest
import org.json.JSONObject
import retrofit2.Response

class RegisterRepository {
    suspend fun registerUser(
        fname: String, lname: String, email: String,
        password: String, deviceToken: String
    ): RegisterDTO {
        val request = RegisterRequest(fname, lname, email, password, deviceToken)
        val response : Response<RegisterDTO> = RetrofitHelper.registerService.register(request)
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