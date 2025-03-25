package com.example.pushnotificationsapp.stepcounter.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pushnotificationsapp.stepcounter.data.models.Journey

@Dao
interface JourneyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourney(journey: Journey)

    @Query("SELECT * FROM journey ORDER BY startTime DESC")
    suspend fun getAllJourneys(): List<Journey>
}