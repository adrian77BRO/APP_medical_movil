package com.example.pushnotificationsapp.appointment.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppointViewModelFactory(
    private val application: Application,
    private val serviceId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppointmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppointmentViewModel(application, serviceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}