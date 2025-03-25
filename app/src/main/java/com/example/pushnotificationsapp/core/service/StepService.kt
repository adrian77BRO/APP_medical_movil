package com.example.pushnotificationsapp.core.service

import android.annotation.SuppressLint
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
import com.example.pushnotificationsapp.core.service.database.StepDB
import com.example.pushnotificationsapp.stepcounter.data.datasource.StepDAO
import com.example.pushnotificationsapp.stepcounter.data.models.Step
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private var startSteps: Int? = null
    private var currentSteps: Int = 0

    // Guardamos la hora de inicio en una variable
    private var startTime: Long = 0

    private lateinit var journeyDao: StepDAO
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        journeyDao = StepDB.getInstance(applicationContext).stepDAO()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundServiceNotification()

        // Registrar el sensor
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)

        // Guardamos la hora de inicio aquí (o en onCreate, si prefieres)
        startTime = System.currentTimeMillis()

        // Reseteamos valores por si se inicia otro viaje
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

        // Calcular la diferencia de pasos
        val diff = if (startSteps == null) 0 else currentSteps - startSteps!!

        // Hora de fin
        val endTime = System.currentTimeMillis()

        // Guardamos el viaje con hora de inicio y hora de fin
        val journey = Step(
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
                    // La primera vez guardamos el valor inicial
                    startSteps = sensorValue
                } else {
                    // Actualizamos el valor final
                    currentSteps = sensorValue
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

/*class StepService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private var startSteps: Int? = null
    private var currentSteps: Int = 0

    private lateinit var journeyDao: StepDAO
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        journeyDao = StepDB.getInstance(applicationContext).stepDAO()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundServiceNotification()

        // Registrar el sensor
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)

        // Reseteamos valores por si se inicia otro viaje
        startSteps = null
        currentSteps = 0

        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
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

        // Calcular la diferencia y guardar en BD
        val diff = if (startSteps == null) 0 else currentSteps - startSteps!!
        val journey = Step(
            startTime = System.currentTimeMillis(), // Podrías guardarlo de otra forma
            endTime = System.currentTimeMillis(),
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
                    // La primera vez guardamos el valor inicial
                    startSteps = sensorValue
                } else {
                    // Actualizamos el valor final
                    currentSteps = sensorValue
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}*/