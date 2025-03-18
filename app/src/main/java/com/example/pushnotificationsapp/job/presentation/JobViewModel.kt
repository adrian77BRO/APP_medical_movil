package com.example.pushnotificationsapp.job.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.core.storage.TokenManager
import com.example.pushnotificationsapp.job.data.models.JobState
import com.example.pushnotificationsapp.job.data.repository.JobsRepository
import kotlinx.coroutines.launch

class JobsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = JobsRepository(TokenManager(application))

    private val _servicesState = MutableLiveData<JobState>(JobState.Idle)
    val servicesState: LiveData<JobState> = _servicesState

    init {
        fetchJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch {
            _servicesState.value = JobState.Loading
            try {
                val response = repository.getJobs()
                _servicesState.value = JobState.Success(response.jobs)
            } catch (e: Exception) {
                _servicesState.value = JobState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}