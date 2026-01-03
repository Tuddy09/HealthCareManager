package com.example.healthcaremanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.healthcaremanager.navigation.AppNavigation
import com.example.healthcaremanager.ui.theme.HealthCareManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthCareManagerTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
