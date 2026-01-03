package com.example.healthcaremanager.data.dao

import androidx.room.*
import com.example.healthcaremanager.data.entity.MedicalRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicalRecord: MedicalRecord): Long

    @Update
    suspend fun update(medicalRecord: MedicalRecord)

    @Delete
    suspend fun delete(medicalRecord: MedicalRecord)

    @Query("SELECT * FROM medical_records WHERE id = :recordId")
    suspend fun getRecordById(recordId: Long): MedicalRecord?

    @Query("SELECT * FROM medical_records WHERE patientId = :patientId ORDER BY date DESC")
    fun getRecordsByPatient(patientId: Long): Flow<List<MedicalRecord>>

    @Query("SELECT * FROM medical_records WHERE doctorId = :doctorId ORDER BY date DESC")
    fun getRecordsByDoctor(doctorId: Long): Flow<List<MedicalRecord>>

    @Query("SELECT * FROM medical_records WHERE appointmentId = :appointmentId")
    suspend fun getRecordByAppointment(appointmentId: Long): MedicalRecord?

    @Query("SELECT * FROM medical_records")
    fun getAllRecords(): Flow<List<MedicalRecord>>
}

