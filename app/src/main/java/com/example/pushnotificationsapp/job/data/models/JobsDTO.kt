package com.example.pushnotificationsapp.job.data.models

data class JobsDTO(
    val status: String,
    val message: String,
    val jobs: List<Job>
)