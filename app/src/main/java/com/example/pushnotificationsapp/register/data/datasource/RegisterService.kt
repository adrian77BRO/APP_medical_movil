package com.example.pushnotificationsapp.register.data.datasource

import com.example.pushnotificationsapp.register.data.models.RegisterDTO
import com.example.pushnotificationsapp.register.data.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("users/register")
    suspend fun register(@Body request : RegisterRequest) : Response<RegisterDTO>
}