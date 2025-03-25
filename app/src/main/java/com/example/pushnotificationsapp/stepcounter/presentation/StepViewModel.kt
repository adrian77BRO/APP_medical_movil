package com.example.pushnotificationsapp.stepcounter.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.core.service.database.StepDB
import com.example.pushnotificationsapp.stepcounter.data.models.Step
import com.example.pushnotificationsapp.stepcounter.data.repository.StepRepository
import kotlinx.coroutines.launch

class StepViewModel(application: Application) : AndroidViewModel(application) {

    private val stepDAO = StepDB.getInstance(application).stepDAO()
    private val repository = StepRepository(stepDAO)

    private val _steps = MutableLiveData<List<Step>>(emptyList())
    val steps: LiveData<List<Step>> get() = _steps

    init {
        loadJourneys()
    }

    fun loadJourneys() {
        viewModelScope.launch {
            val stepsList = repository.getAllSteps()
            _steps.value = stepsList
        }
    }
}