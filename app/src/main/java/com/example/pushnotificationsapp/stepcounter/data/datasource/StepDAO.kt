package com.example.pushnotificationsapp.stepcounter.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pushnotificationsapp.stepcounter.data.models.Step

@Dao
interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourney(step: Step)

    @Query("SELECT * FROM steps ORDER BY startTime DESC")
    suspend fun getAllJourneys(): List<Step>
}

/*@Dao
interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(step: Step)

    @Query("SELECT * FROM steps ORDER BY timestamp DESC")
    suspend fun getAllSteps(): List<Step>
}*/