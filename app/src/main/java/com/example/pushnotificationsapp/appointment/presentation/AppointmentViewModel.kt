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

    private val _appointmentsState = MutableLiveData<AppointmentState>(AppointmentState.Idle)
    val appointmentsState: LiveData<AppointmentState> = _appointmentsState

    init {
        fetchAppointments()
    }

    fun fetchAppointments() {
        viewModelScope.launch {
            _appointmentsState.value = AppointmentState.Loading
            try {
                val response = repository.getAppointments(idJob)
                _appointmentsState.value = AppointmentState.Success(response.appointments)
            } catch (e: Exception) {
                _appointmentsState.value = AppointmentState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}