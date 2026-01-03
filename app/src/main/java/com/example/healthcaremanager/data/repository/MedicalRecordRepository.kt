package com.example.healthcaremanager.data.repository

import com.example.healthcaremanager.data.dao.MedicalRecordDao
import com.example.healthcaremanager.data.entity.MedicalRecord
import kotlinx.coroutines.flow.Flow

class MedicalRecordRepository(private val medicalRecordDao: MedicalRecordDao) {

    suspend fun createRecord(record: MedicalRecord): Long {
        return medicalRecordDao.insert(record)
    }

    suspend fun updateRecord(record: MedicalRecord) {
        medicalRecordDao.update(record)
    }

    fun getRecordsByPatient(patientId: Long): Flow<List<MedicalRecord>> {
        return medicalRecordDao.getRecordsByPatient(patientId)
    }

    fun getRecordsByDoctor(doctorId: Long): Flow<List<MedicalRecord>> {
        return medicalRecordDao.getRecordsByDoctor(doctorId)
    }

    suspend fun getRecordByAppointment(appointmentId: Long): MedicalRecord? {
        return medicalRecordDao.getRecordByAppointment(appointmentId)
    }
}

