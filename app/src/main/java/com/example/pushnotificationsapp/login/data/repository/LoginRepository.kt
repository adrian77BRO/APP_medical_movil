package com.example.pushnotificationsapp.login.data.repository

import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.login.data.models.LoginDTO
import com.example.pushnotificationsapp.login.data.models.LoginRequest
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
            throw Exception("Error al iniciar sesión: ${response.errorBody()?.string()}")
        }
    }
}
/*import android.content.Context
import android.content.SharedPreferences
import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.login.data.models.LoginDTO
import com.example.pushnotificationsapp.login.data.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class LoginRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    suspend fun login(email: String, password: String): Result<LoginDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<LoginDTO> = RetrofitHelper.loginService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            saveUserData(it.user?.username ?: "Usuario", it.token ?: "")
                            return@withContext Result.success(it)
                        }
                        return@withContext Result.failure(Exception(it.message))
                    }
                    return@withContext Result.failure(Exception("Respuesta vacía"))
                } else {
                    val errorMessage = response.errorBody()?.string()?.let {
                        try {
                            JSONObject(it).getString("message")
                        } catch (e: Exception) {
                            "Error desconocido"
                        }
                    } ?: "Error de servidor"
                    return@withContext Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                return@withContext Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }

    private fun saveUserData(username: String, token: String) {
        sharedPreferences.edit().apply {
            putString("username", username)
            putString("token", token)
            apply()
        }
    }
}*/