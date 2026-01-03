package com.example.healthcaremanager.data.repository

import com.example.healthcaremanager.data.dao.UserDao
import com.example.healthcaremanager.data.entity.User
import com.example.healthcaremanager.data.entity.UserRole
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    fun getAllDoctors(): Flow<List<User>> {
        return userDao.getAllDoctors()
    }

    fun getUsersByRole(role: UserRole): Flow<List<User>> {
        return userDao.getUsersByRole(role)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }
}


