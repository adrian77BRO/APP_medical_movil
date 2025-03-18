package com.example.pushnotificationsapp.job.data.datasource

import com.example.pushnotificationsapp.job.data.models.Job
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface JobService {
    @GET("jobs")
    suspend fun getAllMJobs(@Header("Authorization") token: String): Response<Job>
}