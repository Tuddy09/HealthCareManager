package com.example.healthcaremanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthcaremanager.viewmodel.AuthViewModel
import com.example.healthcaremanager.viewmodel.MedicationReminderViewModel
import com.example.healthcaremanager.viewmodel.ReminderState
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    reminderViewModel: MedicationReminderViewModel = viewModel()
) {
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(Calendar.getInstance()) }

    val reminderState by reminderViewModel.reminderState.collectAsState()

    LaunchedEffect(reminderState) {
        if (reminderState is ReminderState.Success) {
            navController.popBackStack()
            reminderViewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Medication Reminder") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Medication Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage (e.g., 1 tablet, 5ml)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frequency (e.g., Once daily, Twice daily)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Reminder Time")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = selectedTime.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0'),
                    onValueChange = {
                        it.toIntOrNull()?.let { hour ->
                            if (hour in 0..23) {
                                selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                            }
                        }
                    },
                    label = { Text("Hour") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = selectedTime.get(Calendar.MINUTE).toString().padStart(2, '0'),
                    onValueChange = {
                        it.toIntOrNull()?.let { minute ->
                            if (minute in 0..59) {
                                selectedTime.set(Calendar.MINUTE, minute)
                            }
                        }
                    },
                    label = { Text("Minute") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (reminderState is ReminderState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        reminderViewModel.createReminder(
                            patientId = authViewModel.getCurrentUserId(),
                            medicationName = medicationName,
                            dosage = dosage,
                            frequency = frequency,
                            timeInMillis = selectedTime.timeInMillis
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = medicationName.isNotBlank() && dosage.isNotBlank() && frequency.isNotBlank()
                ) {
                    Text("Add Reminder", fontSize = 16.sp)
                }
            }

            if (reminderState is ReminderState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (reminderState as ReminderState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

