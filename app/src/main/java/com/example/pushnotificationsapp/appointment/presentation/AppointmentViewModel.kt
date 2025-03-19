package com.example.pushnotificationsapp.appointment.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.appointment.data.models.AppointmentState
import com.example.pushnotificationsapp.appointment.data.repository.AppointmentRepository
import com.example.pushnotificationsapp.core.storage.TokenManager
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application, private val idJob: Int) : AndroidViewModel(application) {
    private val repository = AppointmentRepository(TokenManager(application))

    private val _appointmentState = MutableLiveData<AppointmentState>(AppointmentState.Idle)
    val appointmentState: LiveData<AppointmentState> = _appointmentState

    init {
        fetchAppointments()
    }

    fun fetchAppointments() {
        viewModelScope.launch {
            _appointmentState.value = AppointmentState.Loading
            try {
                val response = repository.getAppointments(idJob)
                _appointmentState.value = AppointmentState.Success(response.appointments)
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun reserve(date: String, observations: String) {
        viewModelScope.launch {
            _appointmentState.value = AppointmentState.Loading
            try {
                val response = repository.reserveAppointment(idJob, date, observations)
                _appointmentState.value = AppointmentState.SuccessForm(response.message)
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}