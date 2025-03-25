package com.example.pushnotificationsapp.stepcounter.data.repository

import com.example.pushnotificationsapp.stepcounter.data.datasource.JourneyDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Journey

class StepRepository(private val journeyDAO: JourneyDAO) {
    suspend fun insertStep(stepData: Journey) {
        journeyDAO.insertJourney(stepData)
    }

    suspend fun getAllSteps(): List<Journey> {
        return journeyDAO.getAllJourneys()
    }
}