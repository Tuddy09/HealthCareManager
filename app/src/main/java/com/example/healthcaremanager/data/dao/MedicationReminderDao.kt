package com.example.healthcaremanager.data.dao

import androidx.room.*
import com.example.healthcaremanager.data.entity.MedicationReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: MedicationReminder): Long

    @Update
    suspend fun update(reminder: MedicationReminder)

    @Delete
    suspend fun delete(reminder: MedicationReminder)

    @Query("SELECT * FROM medication_reminders WHERE id = :reminderId")
    suspend fun getReminderById(reminderId: Long): MedicationReminder?

    @Query("SELECT * FROM medication_reminders WHERE patientId = :patientId ORDER BY timeInMillis ASC")
    fun getRemindersByPatient(patientId: Long): Flow<List<MedicationReminder>>

    @Query("SELECT * FROM medication_reminders WHERE patientId = :patientId AND isActive = 1 ORDER BY timeInMillis ASC")
    fun getActiveRemindersByPatient(patientId: Long): Flow<List<MedicationReminder>>

    @Query("SELECT * FROM medication_reminders")
    fun getAllReminders(): Flow<List<MedicationReminder>>
}

