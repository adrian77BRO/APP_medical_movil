package com.example.pushnotificationsapp.appointment.data.models

sealed class AppointmentState {
    data class Success(val movies: List<Appointment>) : AppointmentState()
    data class Error(val message: String) : AppointmentState()
}