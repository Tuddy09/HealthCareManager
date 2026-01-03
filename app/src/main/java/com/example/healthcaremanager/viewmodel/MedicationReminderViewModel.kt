package com.example.healthcaremanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.healthcaremanager.data.HealthCareDatabase
import com.example.healthcaremanager.data.entity.MedicationReminder
import com.example.healthcaremanager.data.repository.MedicationReminderRepository
import com.example.healthcaremanager.worker.MedicationReminderWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MedicationReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val reminderRepository: MedicationReminderRepository
    private val workManager = WorkManager.getInstance(application)

    private val _reminders = MutableStateFlow<List<MedicationReminder>>(emptyList())
    val reminders: StateFlow<List<MedicationReminder>> = _reminders

    private val _reminderState = MutableStateFlow<ReminderState>(ReminderState.Idle)
    val reminderState: StateFlow<ReminderState> = _reminderState

    init {
        val database = HealthCareDatabase.getDatabase(application)
        reminderRepository = MedicationReminderRepository(database.medicationReminderDao())
    }

    fun loadReminders(patientId: Long) {
        viewModelScope.launch {
            reminderRepository.getRemindersByPatient(patientId).collect { reminderList ->
                _reminders.value = reminderList
            }
        }
    }

    fun createReminder(
        patientId: Long,
        medicationName: String,
        dosage: String,
        frequency: String,
        timeInMillis: Long
    ) {
        viewModelScope.launch {
            try {
                _reminderState.value = ReminderState.Loading
                val reminder = MedicationReminder(
                    patientId = patientId,
                    medicationName = medicationName,
                    dosage = dosage,
                    frequency = frequency,
                    timeInMillis = timeInMillis,
                    isActive = true
                )
                val reminderId = reminderRepository.createReminder(reminder)

                // Schedule notification
                scheduleNotification(reminderId, medicationName, dosage, timeInMillis)

                _reminderState.value = ReminderState.Success("Reminder created successfully")
            } catch (e: Exception) {
                _reminderState.value = ReminderState.Error(e.message ?: "Failed to create reminder")
            }
        }
    }

    fun deleteReminder(reminder: MedicationReminder) {
        viewModelScope.launch {
            try {
                reminderRepository.deleteReminder(reminder)
                _reminderState.value = ReminderState.Success("Reminder deleted")
            } catch (e: Exception) {
                _reminderState.value = ReminderState.Error(e.message ?: "Failed to delete reminder")
            }
        }
    }

    fun toggleReminderActive(reminder: MedicationReminder) {
        viewModelScope.launch {
            try {
                val updatedReminder = reminder.copy(isActive = !reminder.isActive)
                reminderRepository.updateReminder(updatedReminder)
            } catch (e: Exception) {
                _reminderState.value = ReminderState.Error(e.message ?: "Failed to update reminder")
            }
        }
    }

    private fun scheduleNotification(reminderId: Long, medicationName: String, dosage: String, timeInMillis: Long) {
        val currentTime = System.currentTimeMillis()
        val delay = timeInMillis - currentTime

        if (delay > 0) {
            val inputData = Data.Builder()
                .putString(MedicationReminderWorker.KEY_MEDICATION_NAME, medicationName)
                .putString(MedicationReminderWorker.KEY_DOSAGE, dosage)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<MedicationReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .addTag("reminder_$reminderId")
                .build()

            workManager.enqueue(workRequest)
        }
    }

    fun resetState() {
        _reminderState.value = ReminderState.Idle
    }
}

sealed class ReminderState {
    object Idle : ReminderState()
    object Loading : ReminderState()
    data class Success(val message: String) : ReminderState()
    data class Error(val message: String) : ReminderState()
}

