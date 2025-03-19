package com.example.pushnotificationsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pushnotificationsapp.core.navigation.AppNavigation
import com.example.pushnotificationsapp.ui.theme.PushNotificationsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PushNotificationsAppTheme {
                AppNavigation()
            }
        }
    }
}

/*fun getFirebaseToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.d("FCM", "Fetching FCM token failed", task.exception)
            return@addOnCompleteListener
        }
        val token = task.result
        Log.d("FCM Token", token ?: "No Token")
    }
}*/