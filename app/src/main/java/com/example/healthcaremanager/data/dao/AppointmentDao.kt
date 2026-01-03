package com.example.healthcaremanager.data.dao

import androidx.room.*
import com.example.healthcaremanager.data.entity.Appointment
import com.example.healthcaremanager.data.entity.AppointmentStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment): Long

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE id = :appointmentId")
    suspend fun getAppointmentById(appointmentId: Long): Appointment?

    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY dateTime DESC")
    fun getAppointmentsByPatient(patientId: Long): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId ORDER BY dateTime DESC")
    fun getAppointmentsByDoctor(doctorId: Long): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId AND status = :status ORDER BY dateTime ASC")
    fun getAppointmentsByDoctorAndStatus(doctorId: Long, status: AppointmentStatus): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE patientId = :patientId AND status = :status ORDER BY dateTime DESC")
    fun getAppointmentsByPatientAndStatus(patientId: Long, status: AppointmentStatus): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): Flow<List<Appointment>>
}

