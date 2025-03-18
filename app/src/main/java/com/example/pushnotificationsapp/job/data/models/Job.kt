package com.example.pushnotificationsapp.job.data.models

data class Job(
    val id_job: Int,
    val title: String,
    val description: String,
    val cost: Double,
    val doctor: String
)
