package com.example.pushnotificationsapp.appointment.presentation

/*import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.appointment.data.models.TaskRequest
import com.example.pushnotificationsapp.appointment.data.models.TasksDTO
import com.example.pushnotificationsapp.appointment.data.repository.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppointmentRepository(application.applicationContext)

    private val _tasks = MutableLiveData<TasksDTO>()
    val tasks: LiveData<TasksDTO> get() = _tasks

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> get() = _success

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getTasks()
                response.onSuccess {
                    _tasks.postValue(it)
                }.onFailure {
                    _error.postValue("Error al cargar las tareas: ${it.message}")
                }
            } catch (e: Exception) {
                _error.postValue("Error al cargar las tareas")
            }
        }
    }

    fun addTask(task: TaskRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createTask(task)
                response.onSuccess {
                    loadTasks()
                    _success.postValue(it.message)
                }.onFailure {
                    _error.postValue("Error al crear la tarea: ${it.message}")
                }
            } catch (e: Exception) {
                _error.postValue("Error al crear las tarea")
            }
        }
    }
}*/