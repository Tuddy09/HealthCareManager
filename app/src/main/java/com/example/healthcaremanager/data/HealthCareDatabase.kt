package com.example.healthcaremanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthcaremanager.data.dao.*
import com.example.healthcaremanager.data.entity.*

@Database(
    entities = [
        User::class,
        Appointment::class,
        MedicalRecord::class,
        MedicationReminder::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HealthCareDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicalRecordDao(): MedicalRecordDao
    abstract fun medicationReminderDao(): MedicationReminderDao

    companion object {
        @Volatile
        private var INSTANCE: HealthCareDatabase? = null

        fun getDatabase(context: Context): HealthCareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthCareDatabase::class.java,
                    "healthcare_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

