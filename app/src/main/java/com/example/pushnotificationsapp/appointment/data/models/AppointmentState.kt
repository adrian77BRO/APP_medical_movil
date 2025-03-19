package com.example.pushnotificationsapp.appointment.data.models

sealed class AppointmentState {
    object Idle : AppointmentState()
    object Loading : AppointmentState()
    data class Success(val appointments: List<Appointment>) : AppointmentState()
    data class SuccessForm(val message: String) : AppointmentState()
    data class Error(val message: String) : AppointmentState()
}