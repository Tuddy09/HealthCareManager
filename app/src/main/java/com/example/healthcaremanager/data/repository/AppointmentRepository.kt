package com.example.healthcaremanager.data.repository

import com.example.healthcaremanager.data.dao.AppointmentDao
import com.example.healthcaremanager.data.entity.Appointment
import com.example.healthcaremanager.data.entity.AppointmentStatus
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val appointmentDao: AppointmentDao) {

    suspend fun createAppointment(appointment: Appointment): Long {
        return appointmentDao.insert(appointment)
    }

    suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.update(appointment)
    }

    suspend fun getAppointmentById(appointmentId: Long): Appointment? {
        return appointmentDao.getAppointmentById(appointmentId)
    }

    fun getAppointmentsByPatient(patientId: Long): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByPatient(patientId)
    }

    fun getAppointmentsByDoctor(doctorId: Long): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByDoctor(doctorId)
    }

    fun getAppointmentsByDoctorAndStatus(doctorId: Long, status: AppointmentStatus): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByDoctorAndStatus(doctorId, status)
    }

    fun getAppointmentsByPatientAndStatus(patientId: Long, status: AppointmentStatus): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByPatientAndStatus(patientId, status)
    }

    suspend fun approveAppointment(appointmentId: Long) {
        val appointment = appointmentDao.getAppointmentById(appointmentId)
        appointment?.let {
            appointmentDao.update(it.copy(status = AppointmentStatus.APPROVED))
        }
    }

    suspend fun rejectAppointment(appointmentId: Long) {
        val appointment = appointmentDao.getAppointmentById(appointmentId)
        appointment?.let {
            appointmentDao.update(it.copy(status = AppointmentStatus.REJECTED))
        }
    }

    suspend fun completeAppointment(appointmentId: Long) {
        val appointment = appointmentDao.getAppointmentById(appointmentId)
        appointment?.let {
            appointmentDao.update(it.copy(status = AppointmentStatus.COMPLETED))
        }
    }
}

