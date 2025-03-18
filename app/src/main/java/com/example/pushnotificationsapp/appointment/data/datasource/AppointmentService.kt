package com.example.pushnotificationsapp.appointment.data.datasource

import com.example.pushnotificationsapp.appointment.data.models.AppointmentDTO
import com.example.pushnotificationsapp.appointment.data.models.AppointmentRequest
import com.example.pushnotificationsapp.appointment.data.models.AppointmentsDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentService {

    @GET("appoints/{job}")
    suspend fun getAppointmentsByJob(
        @Header("Authorization") token: String,
        @Path("job") idJob: Int
    ): Response<AppointmentsDTO>

    @POST("appoints")
    suspend fun createAppointment(
        @Header("Authorization") token: String,
        @Body appointment: AppointmentRequest
    ): Response<AppointmentDTO>
}