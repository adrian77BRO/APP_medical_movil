package com.example.pushnotificationsapp.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.pushnotificationsapp.R
import com.example.pushnotificationsapp.core.service.database.StepCounterDB
import com.example.pushnotificationsapp.stepcounter.data.datasource.JourneyDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Journey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private var startSteps: Int? = null
    private var currentSteps: Int = 0
    private var startTime: Long = 0

    private lateinit var journeyDao: JourneyDAO
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        journeyDao = StepCounterDB.getInstance(applicationContext).journeyDAO()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundServiceNotification()
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        startTime = System.currentTimeMillis()
        startSteps = null
        currentSteps = 0
        return START_STICKY
    }

    private fun startForegroundServiceNotification() {
        val channelId = "step_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Step Counter Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Contando pasos...")
            .setContentText("Tu viaje está en curso")
            .setSmallIcon(R.drawable.stepcounter)
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)

        val diff = if (startSteps == null) 0 else currentSteps - startSteps!!
        val endTime = System.currentTimeMillis()

        val journey = Journey(
            startTime = startTime,
            endTime = endTime,
            totalSteps = diff
        )
        serviceScope.launch {
            journeyDao.insertJourney(journey)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val sensorValue = it.values[0].toInt()
                if (startSteps == null) {
                    startSteps = sensorValue
                } else {
                    currentSteps = sensorValue
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}