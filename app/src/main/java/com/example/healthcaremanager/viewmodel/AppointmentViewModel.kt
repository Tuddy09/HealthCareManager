package com.example.healthcaremanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcaremanager.data.HealthCareDatabase
import com.example.healthcaremanager.data.entity.Appointment
import com.example.healthcaremanager.data.entity.AppointmentStatus
import com.example.healthcaremanager.data.entity.User
import com.example.healthcaremanager.data.repository.AppointmentRepository
import com.example.healthcaremanager.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val appointmentRepository: AppointmentRepository
    private val userRepository: UserRepository

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _doctors = MutableStateFlow<List<User>>(emptyList())
    val doctors: StateFlow<List<User>> = _doctors

    private val _appointmentState = MutableStateFlow<AppointmentState>(AppointmentState.Idle)
    val appointmentState: StateFlow<AppointmentState> = _appointmentState

    init {
        val database = HealthCareDatabase.getDatabase(application)
        appointmentRepository = AppointmentRepository(database.appointmentDao())
        userRepository = UserRepository(database.userDao())
    }

    fun loadDoctors() {
        viewModelScope.launch {
            userRepository.getAllDoctors().collect { doctorList ->
                _doctors.value = doctorList
            }
        }
    }

    fun loadPatientAppointments(patientId: Long) {
        viewModelScope.launch {
            appointmentRepository.getAppointmentsByPatient(patientId).collect { appointmentList ->
                _appointments.value = appointmentList
            }
        }
    }

    fun loadDoctorAppointments(doctorId: Long) {
        viewModelScope.launch {
            appointmentRepository.getAppointmentsByDoctor(doctorId).collect { appointmentList ->
                _appointments.value = appointmentList
            }
        }
    }

    fun loadPendingAppointments(doctorId: Long) {
        viewModelScope.launch {
            appointmentRepository.getAppointmentsByDoctorAndStatus(doctorId, AppointmentStatus.PENDING)
                .collect { appointmentList ->
                    _appointments.value = appointmentList
                }
        }
    }

    fun createAppointment(patientId: Long, doctorId: Long, dateTime: Long, notes: String) {
        viewModelScope.launch {
            try {
                _appointmentState.value = AppointmentState.Loading
                val appointment = Appointment(
                    patientId = patientId,
                    doctorId = doctorId,
                    dateTime = dateTime,
                    status = AppointmentStatus.PENDING,
                    notes = notes
                )
                appointmentRepository.createAppointment(appointment)
                _appointmentState.value = AppointmentState.Success("Appointment booked successfully")
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Failed to book appointment")
            }
        }
    }

    fun approveAppointment(appointmentId: Long) {
        viewModelScope.launch {
            try {
                appointmentRepository.approveAppointment(appointmentId)
                _appointmentState.value = AppointmentState.Success("Appointment approved")
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Failed to approve")
            }
        }
    }

    fun rejectAppointment(appointmentId: Long) {
        viewModelScope.launch {
            try {
                appointmentRepository.rejectAppointment(appointmentId)
                _appointmentState.value = AppointmentState.Success("Appointment rejected")
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Failed to reject")
            }
        }
    }

    fun completeAppointment(appointmentId: Long) {
        viewModelScope.launch {
            try {
                appointmentRepository.completeAppointment(appointmentId)
                _appointmentState.value = AppointmentState.Success("Appointment completed")
            } catch (e: Exception) {
                _appointmentState.value = AppointmentState.Error(e.message ?: "Failed to complete")
            }
        }
    }

    suspend fun getDoctorName(doctorId: Long): String {
        return userRepository.getUserById(doctorId)?.name ?: "Unknown Doctor"
    }

    suspend fun getPatientName(patientId: Long): String {
        return userRepository.getUserById(patientId)?.name ?: "Unknown Patient"
    }

    suspend fun getAppointmentById(appointmentId: Long): Appointment? {
        return appointmentRepository.getAppointmentById(appointmentId)
    }

    fun resetState() {
        _appointmentState.value = AppointmentState.Idle
    }
}

sealed class AppointmentState {
    object Idle : AppointmentState()
    object Loading : AppointmentState()
    data class Success(val message: String) : AppointmentState()
    data class Error(val message: String) : AppointmentState()
}

