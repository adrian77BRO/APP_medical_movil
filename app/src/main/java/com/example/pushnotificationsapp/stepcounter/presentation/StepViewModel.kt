package com.example.pushnotificationsapp.stepcounter.presentation

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotificationsapp.core.service.StepService
import com.example.pushnotificationsapp.core.service.database.StepCounterDB
import com.example.pushnotificationsapp.stepcounter.data.models.Journey
import com.example.pushnotificationsapp.stepcounter.data.repository.StepRepository
import kotlinx.coroutines.launch

class StepViewModel(application: Application) : AndroidViewModel(application) {

    private val journeyDAO = StepCounterDB.getInstance(application).journeyDAO()
    private val repository = StepRepository(journeyDAO)

    private val _journeys = MutableLiveData<List<Journey>>(emptyList())
    val journeys: LiveData<List<Journey>> get() = _journeys

    private val _journeyActive = MutableLiveData(false)
    val journeyActive: LiveData<Boolean> = _journeyActive

    init {
        loadJourneys()
    }

    fun loadJourneys() {
        viewModelScope.launch {
            val stepsList = repository.getAllSteps()
            _journeys.value = stepsList
        }
    }

    fun startJourney() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, StepService::class.java)
        ContextCompat.startForegroundService(context, intent)
        _journeyActive.value = true
    }

    fun endJourney() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, StepService::class.java)
        context.stopService(intent)
        loadJourneys()
        _journeyActive.value = false
    }
}