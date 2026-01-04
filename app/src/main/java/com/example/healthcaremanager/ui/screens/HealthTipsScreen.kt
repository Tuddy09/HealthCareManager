package com.example.healthcaremanager.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthcaremanager.ui.theme.GradientStart
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthTipsScreen(navController: NavController) {
    val healthTips = listOf(
        HealthTip(
            "💧 Stay Hydrated",
            "Drink at least 8 glasses of water daily to maintain proper body functions and energy levels.",
            Color(0xFF42A5F5)
        ),
        HealthTip(
            "🏃 Regular Exercise",
            "Aim for at least 30 minutes of moderate exercise 5 days a week to maintain cardiovascular health.",
            Color(0xFFFF7043)
        ),
        HealthTip(
            "🥗 Balanced Diet",
            "Include fruits, vegetables, whole grains, and lean proteins in your daily meals.",
            Color(0xFF66BB6A)
        ),
        HealthTip(
            "😴 Adequate Sleep",
            "Get 7-9 hours of quality sleep each night to support physical and mental health.",
            Color(0xFF7E57C2)
        ),
        HealthTip(
            "🧘 Stress Management",
            "Practice relaxation techniques like meditation, deep breathing, or yoga to reduce stress.",
            Color(0xFF26A69A)
        ),
        HealthTip(
            "🩺 Regular Check-ups",
            "Visit your doctor for regular health screenings and preventive care.",
            Color(0xFFEF5350)
        ),
        HealthTip(
            "🧼 Hand Hygiene",
            "Wash your hands frequently with soap and water for at least 20 seconds.",
            Color(0xFF29B6F6)
        ),
        HealthTip(
            "📱 Limit Screen Time",
            "Take regular breaks from screens to reduce eye strain and maintain good posture.",
            Color(0xFFFF9800)
        ),
        HealthTip(
            "🚭 Quit Smoking",
            "Avoid tobacco in all forms to reduce risk of heart disease, cancer, and respiratory problems.",
            Color(0xFFE53935)
        ),
        HealthTip(
            "🧠 Mental Health",
            "Don't hesitate to seek professional help if you're feeling anxious, depressed, or overwhelmed.",
            Color(0xFF5C6BC0)
        ),
        HealthTip(
            "☀️ Sun Protection",
            "Use sunscreen with SPF 30+ and wear protective clothing when outdoors.",
            Color(0xFFFFA726)
        ),
        HealthTip(
            "🦷 Dental Care",
            "Brush twice daily and floss regularly to maintain oral health.",
            Color(0xFF42A5F5)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Tips", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(healthTips) { index, tip ->
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(index * 50L)
                    isVisible = true
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(400)) +
                            slideInHorizontally(
                                initialOffsetX = { if (index % 2 == 0) -it else it },
                                animationSpec = tween(400)
                            )
                ) {
                    HealthTipCard(tip)
                }
            }
        }
    }
}

@Composable
fun HealthTipCard(tip: HealthTip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                tip.color.copy(alpha = 0.8f),
                                tip.color
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tip.title.take(2),
                    fontSize = 24.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tip.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tip.description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class HealthTip(
    val title: String,
    val description: String,
    val color: Color
)

