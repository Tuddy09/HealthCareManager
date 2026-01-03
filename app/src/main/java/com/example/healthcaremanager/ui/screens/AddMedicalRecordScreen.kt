package com.example.healthcaremanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthcaremanager.viewmodel.AuthViewModel
import com.example.healthcaremanager.viewmodel.MedicalRecordViewModel
import com.example.healthcaremanager.viewmodel.RecordState
import com.example.healthcaremanager.viewmodel.AppointmentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicalRecordScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    appointmentViewModel: AppointmentViewModel = viewModel(),
    medicalRecordViewModel: MedicalRecordViewModel = viewModel(),
    appointmentId: Long
) {
    var diagnosis by remember { mutableStateOf("") }
    var prescription by remember { mutableStateOf("") }
    var treatmentNotes by remember { mutableStateOf("") }
    var patientId by remember { mutableStateOf(0L) }

    val recordState by medicalRecordViewModel.recordState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(appointmentId) {
        scope.launch {
            val appointment = appointmentViewModel.getAppointmentById(appointmentId)
            appointment?.let {
                patientId = it.patientId
            }
        }
    }

    LaunchedEffect(recordState) {
        if (recordState is RecordState.Success) {
            navController.popBackStack()
            medicalRecordViewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Medical Record") },
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
                value = diagnosis,
                onValueChange = { diagnosis = it },
                label = { Text("Diagnosis") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = prescription,
                onValueChange = { prescription = it },
                label = { Text("Prescription") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = treatmentNotes,
                onValueChange = { treatmentNotes = it },
                label = { Text("Treatment Notes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (recordState is RecordState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        medicalRecordViewModel.createRecord(
                            patientId = patientId,
                            doctorId = authViewModel.getCurrentUserId(),
                            appointmentId = appointmentId,
                            diagnosis = diagnosis,
                            prescription = prescription,
                            treatmentNotes = treatmentNotes
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = diagnosis.isNotBlank() && prescription.isNotBlank() && treatmentNotes.isNotBlank()
                ) {
                    Text("Save Medical Record", fontSize = 16.sp)
                }
            }

            if (recordState is RecordState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (recordState as RecordState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

