package com.example.pushnotificationsapp.job.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.core.storage.TokenManager
import com.example.pushnotificationsapp.job.data.models.JobState
import com.example.pushnotificationsapp.job.data.repository.JobRepository
import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = JobRepository(TokenManager(application))

    private val _jobsState = MutableLiveData<JobState>(JobState.Idle)
    val jobsState: LiveData<JobState> = _jobsState

    init {
        fetchJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch {
            _jobsState.value = JobState.Loading
            try {
                val response = repository.getJobs()
                _jobsState.value = JobState.Success(response.jobs)
            } catch (e: Exception) {
                _jobsState.value = JobState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}