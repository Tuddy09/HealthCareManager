package com.example.healthcaremanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcaremanager.data.HealthCareDatabase
import com.example.healthcaremanager.data.entity.MedicalRecord
import com.example.healthcaremanager.data.repository.MedicalRecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicalRecordViewModel(application: Application) : AndroidViewModel(application) {
    private val medicalRecordRepository: MedicalRecordRepository

    private val _records = MutableStateFlow<List<MedicalRecord>>(emptyList())
    val records: StateFlow<List<MedicalRecord>> = _records

    private val _recordState = MutableStateFlow<RecordState>(RecordState.Idle)
    val recordState: StateFlow<RecordState> = _recordState

    init {
        val database = HealthCareDatabase.getDatabase(application)
        medicalRecordRepository = MedicalRecordRepository(database.medicalRecordDao())
    }

    fun loadPatientRecords(patientId: Long) {
        viewModelScope.launch {
            medicalRecordRepository.getRecordsByPatient(patientId).collect { recordList ->
                _records.value = recordList
            }
        }
    }

    fun loadDoctorRecords(doctorId: Long) {
        viewModelScope.launch {
            medicalRecordRepository.getRecordsByDoctor(doctorId).collect { recordList ->
                _records.value = recordList
            }
        }
    }

    fun createRecord(
        patientId: Long,
        doctorId: Long,
        appointmentId: Long,
        diagnosis: String,
        prescription: String,
        treatmentNotes: String
    ) {
        viewModelScope.launch {
            try {
                _recordState.value = RecordState.Loading
                val record = MedicalRecord(
                    patientId = patientId,
                    doctorId = doctorId,
                    appointmentId = appointmentId,
                    date = System.currentTimeMillis(),
                    diagnosis = diagnosis,
                    prescription = prescription,
                    treatmentNotes = treatmentNotes
                )
                medicalRecordRepository.createRecord(record)
                _recordState.value = RecordState.Success("Medical record created successfully")
            } catch (e: Exception) {
                _recordState.value = RecordState.Error(e.message ?: "Failed to create record")
            }
        }
    }

    fun resetState() {
        _recordState.value = RecordState.Idle
    }
}

sealed class RecordState {
    object Idle : RecordState()
    object Loading : RecordState()
    data class Success(val message: String) : RecordState()
    data class Error(val message: String) : RecordState()
}


