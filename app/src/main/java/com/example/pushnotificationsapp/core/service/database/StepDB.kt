package com.example.pushnotificationsapp.core.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pushnotificationsapp.stepcounter.data.datasource.StepDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Step

@Database(entities = [Step::class], version = 2)
abstract class StepDB : RoomDatabase() {
    abstract fun stepDAO(): StepDAO

    companion object {
        @Volatile
        private var INSTANCE: StepDB? = null

        fun getInstance(context: Context): StepDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepDB::class.java,
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