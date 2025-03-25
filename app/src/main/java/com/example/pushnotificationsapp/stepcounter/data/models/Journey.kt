package com.example.pushnotificationsapp.stepcounter.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journey")
data class Journey(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTime: Long,
    val endTime: Long,
    val totalSteps: Int
)