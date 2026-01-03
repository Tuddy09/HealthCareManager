package com.example.healthcaremanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthTipsScreen(navController: NavController) {
    val healthTips = listOf(
        HealthTip(
            "Stay Hydrated",
            "Drink at least 8 glasses of water daily to maintain proper body functions and energy levels."
        ),
        HealthTip(
            "Regular Exercise",
            "Aim for at least 30 minutes of moderate exercise 5 days a week to maintain cardiovascular health."
        ),
        HealthTip(
            "Balanced Diet",
            "Include fruits, vegetables, whole grains, and lean proteins in your daily meals."
        ),
        HealthTip(
            "Adequate Sleep",
            "Get 7-9 hours of quality sleep each night to support physical and mental health."
        ),
        HealthTip(
            "Stress Management",
            "Practice relaxation techniques like meditation, deep breathing, or yoga to reduce stress."
        ),
        HealthTip(
            "Regular Check-ups",
            "Visit your doctor for regular health screenings and preventive care."
        ),
        HealthTip(
            "Hand Hygiene",
            "Wash your hands frequently with soap and water for at least 20 seconds."
        ),
        HealthTip(
            "Limit Screen Time",
            "Take regular breaks from screens to reduce eye strain and maintain good posture."
        ),
        HealthTip(
            "Quit Smoking",
            "Avoid tobacco in all forms to reduce risk of heart disease, cancer, and respiratory problems."
        ),
        HealthTip(
            "Mental Health",
            "Don't hesitate to seek professional help if you're feeling anxious, depressed, or overwhelmed."
        ),
        HealthTip(
            "Sun Protection",
            "Use sunscreen with SPF 30+ and wear protective clothing when outdoors."
        ),
        HealthTip(
            "Dental Care",
            "Brush twice daily and floss regularly to maintain oral health."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Tips") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(healthTips) { tip ->
                HealthTipCard(tip)
            }
        }
    }
}

@Composable
fun HealthTipCard(tip: HealthTip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "💡 ${tip.title}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.description,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

data class HealthTip(
    val title: String,
    val description: String
)

