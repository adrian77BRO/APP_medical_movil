package com.example.pushnotificationsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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