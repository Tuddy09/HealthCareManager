package com.example.healthcaremanager.data

import androidx.room.TypeConverter
import com.example.healthcaremanager.data.entity.AppointmentStatus
import com.example.healthcaremanager.data.entity.UserRole

class Converters {
    @TypeConverter
    fun fromUserRole(value: UserRole): String {
        return value.name
    }

    @TypeConverter
    fun toUserRole(value: String): UserRole {
        return UserRole.valueOf(value)
    }

    @TypeConverter
    fun fromAppointmentStatus(value: AppointmentStatus): String {
        return value.name
    }

    @TypeConverter
    fun toAppointmentStatus(value: String): AppointmentStatus {
        return AppointmentStatus.valueOf(value)
    }
}


