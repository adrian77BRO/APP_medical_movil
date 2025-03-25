package com.example.pushnotificationsapp.stepcounter.data.repository

import com.example.pushnotificationsapp.stepcounter.data.datasource.StepDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Step

class StepRepository(private val stepDAO: StepDAO) {
    suspend fun insertStep(stepData: Step) {
        stepDAO.insertJourney(stepData)
    }

    suspend fun getAllSteps(): List<Step> {
        return stepDAO.getAllJourneys()
    }
}