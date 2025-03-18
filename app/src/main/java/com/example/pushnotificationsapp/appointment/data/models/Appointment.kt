package com.example.pushnotificationsapp.appointment.data.models

data class Appointment(
    val id_appointment: Int,
    val date_appoint: String,
    val observations: String,
    val status: Int,
    val id_job: Int,
)