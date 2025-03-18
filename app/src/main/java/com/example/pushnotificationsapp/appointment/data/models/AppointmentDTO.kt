package com.example.pushnotificationsapp.appointment.data.models

data class AppointmentDTO(
    val status: String,
    val message: String,
    val appointment: Appointment?,
)

data class AppointmentsDTO(
    val status: String,
    val message: String,
    val appointments: List<Appointment>
)