package com.example.pushnotificationsapp.job.data.models

sealed class JobState {
    object Idle : JobState()
    object Loading : JobState()
    data class Success(val jobs: List<Job>) : JobState()
    data class Error(val message: String) : JobState()
}