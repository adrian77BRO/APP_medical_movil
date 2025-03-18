package com.example.pushnotificationsapp.appointment.data.repository

/*import android.content.Context
import com.example.pushnotificationsapp.appointment.data.models.AppointmentsDTO
import com.example.pushnotificationsapp.core.network.RetrofitHelper
import com.example.pushnotificationsapp.appointment.data.models.TaskDTO
import com.example.pushnotificationsapp.appointment.data.models.TaskRequest
import com.example.pushnotificationsapp.appointment.data.models.TasksDTO

class AppointmentRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val token: String = sharedPreferences.getString("token", "") ?: ""
    private val api = RetrofitHelper.appointmentService

    suspend fun getAppointments(): Result<AppointmentsDTO> {
        return try {
            val response = api.getAppointmentsByJob(token)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createAppointment(task: TaskRequest): Result<TaskDTO> {
        return try {
            val response = api.createTask(token, task)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}*/