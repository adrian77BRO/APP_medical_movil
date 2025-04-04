package com.example.pushnotificationsapp.appointment.data.repository

import com.example.pushnotificationsapp.appointment.data.models.AppointmentDTO
import com.example.pushnotificationsapp.appointment.data.models.AppointmentRequest
import com.example.pushnotificationsapp.appointment.data.models.AppointmentsDTO
import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.core.storage.TokenManager

class AppointmentRepository(private val tokenManager: TokenManager) {
    suspend fun getAppointments(idJob: Int): AppointmentsDTO {
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) throw Exception("No se encontró el token del usuario")
        val response = RetrofitHelper.appointmentService.getAppointmentsByJob(token, idJob)
        if (response.isSuccessful) {
            response.body()?.let { return it }
            throw Exception("La respuesta está vacía")
        } else {
            throw Exception("Error al obtener citas: ${response.errorBody()?.string()}")
        }
    }

    suspend fun reserveAppointment(idJob: Int, date: String, observations: String): AppointmentDTO {
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) throw Exception("No se encontró el token del usuario")
        val request = AppointmentRequest(date, observations, idJob)
        val response = RetrofitHelper.appointmentService.createAppointment(token, request)
        if (response.isSuccessful) {
            response.body()?.let { return it }
            throw Exception("La respuesta está vacía")
        } else {
            throw Exception("Error al reservar cita: ${response.errorBody()?.string()}")
        }
    }
}