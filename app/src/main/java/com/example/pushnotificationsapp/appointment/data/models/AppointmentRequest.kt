package com.example.pushnotificationsapp.appointment.data.models

data class AppointmentRequest(
    val date_appoint: String,
    val observations: String,
    val id_job: Int,
)