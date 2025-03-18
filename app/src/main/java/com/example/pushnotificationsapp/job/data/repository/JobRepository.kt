package com.example.pushnotificationsapp.job.data.repository

import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.core.storage.TokenManager
import com.example.pushnotificationsapp.job.data.models.JobsDTO

class JobsRepository(private val tokenManager: TokenManager) {
    suspend fun getJobs(): JobsDTO {
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) throw Exception("No se encontró el token del usuario")
        val response = RetrofitHelper.jobService.getAllJobs(token)
        if (response.isSuccessful) {
            response.body()?.let { return it }
            throw Exception("La respuesta está vacía")
        } else {
            throw Exception("Error al obtener servicios: ${response.errorBody()?.string()}")
        }
    }
}