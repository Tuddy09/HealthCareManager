package com.example.healthcaremanager.data.repository

import com.example.healthcaremanager.data.dao.MedicationReminderDao
import com.example.healthcaremanager.data.entity.MedicationReminder
import kotlinx.coroutines.flow.Flow

class MedicationReminderRepository(private val medicationReminderDao: MedicationReminderDao) {

    suspend fun createReminder(reminder: MedicationReminder): Long {
        return medicationReminderDao.insert(reminder)
    }

    suspend fun updateReminder(reminder: MedicationReminder) {
        medicationReminderDao.update(reminder)
    }

    suspend fun deleteReminder(reminder: MedicationReminder) {
        medicationReminderDao.delete(reminder)
    }

    fun getRemindersByPatient(patientId: Long): Flow<List<MedicationReminder>> {
        return medicationReminderDao.getRemindersByPatient(patientId)
    }

    fun getActiveRemindersByPatient(patientId: Long): Flow<List<MedicationReminder>> {
        return medicationReminderDao.getActiveRemindersByPatient(patientId)
    }

    suspend fun getReminderById(reminderId: Long): MedicationReminder? {
        return medicationReminderDao.getReminderById(reminderId)
    }
}

