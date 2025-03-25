package com.example.pushnotificationsapp.core.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pushnotificationsapp.stepcounter.data.datasource.JourneyDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Journey

@Database(entities = [Journey::class], version = 3)
abstract class StepCounterDB : RoomDatabase() {
    abstract fun journeyDAO(): JourneyDAO

    companion object {
        @Volatile
        private var INSTANCE: StepCounterDB? = null

        fun getInstance(context: Context): StepCounterDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepCounterDB::class.java,
                    "steps_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}