package com.example.healthcaremanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val gender: String,
    val contact: String,
    val role: UserRole // PATIENT or DOCTOR
)

enum class UserRole {
    PATIENT,
    DOCTOR
}


