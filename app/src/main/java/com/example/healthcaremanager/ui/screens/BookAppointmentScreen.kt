package com.example.healthcaremanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthcaremanager.data.entity.User
import com.example.healthcaremanager.viewmodel.AppointmentState
import com.example.healthcaremanager.viewmodel.AppointmentViewModel
import com.example.healthcaremanager.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val doctors by appointmentViewModel.doctors.collectAsState()
    val appointmentState by appointmentViewModel.appointmentState.collectAsState()

    var selectedDoctor by remember { mutableStateOf<User?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        appointmentViewModel.loadDoctors()
    }

    LaunchedEffect(appointmentState) {
        if (appointmentState is AppointmentState.Success) {
            navController.popBackStack()
            appointmentViewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment") },
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
        ) {
            Text(
                text = "Select Doctor",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDoctor?.name ?: "Select a doctor",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    doctors.forEach { doctor ->
                        DropdownMenuItem(
                            text = { Text("Dr. ${doctor.name}") },
                            onClick = {
                                selectedDoctor = doctor
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Appointment Date",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(selectedDate)))
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (appointmentState is AppointmentState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        selectedDoctor?.let { doctor ->
                            appointmentViewModel.createAppointment(
                                patientId = authViewModel.getCurrentUserId(),
                                doctorId = doctor.id,
                                dateTime = selectedDate,
                                notes = notes
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = selectedDoctor != null
                ) {
                    Text("Book Appointment", fontSize = 16.sp)
                }
            }

            if (appointmentState is AppointmentState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (appointmentState as AppointmentState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

