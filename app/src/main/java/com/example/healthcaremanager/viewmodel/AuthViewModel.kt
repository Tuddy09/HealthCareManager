package com.example.healthcaremanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcaremanager.data.HealthCareDatabase
import com.example.healthcaremanager.data.UserPreferences
import com.example.healthcaremanager.data.entity.User
import com.example.healthcaremanager.data.entity.UserRole
import com.example.healthcaremanager.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    private val userPreferences: UserPreferences = UserPreferences(application)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoggedIn = MutableStateFlow(userPreferences.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        val database = HealthCareDatabase.getDatabase(application)
        userRepository = UserRepository(database.userDao())
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val user = userRepository.login(email, password)
                if (user != null) {
                    userPreferences.saveUserSession(user.id, user.role.name)
                    _isLoggedIn.value = true
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun register(name: String, email: String, password: String, age: Int, gender: String, contact: String, role: UserRole) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading

                // Check if email already exists
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    _authState.value = AuthState.Error("Email already registered")
                    return@launch
                }

                val user = User(
                    name = name,
                    email = email,
                    password = password,
                    age = age,
                    gender = gender,
                    contact = contact,
                    role = role
                )
                val userId = userRepository.registerUser(user)
                userPreferences.saveUserSession(userId, role.name)
                _isLoggedIn.value = true
                _authState.value = AuthState.Success(user.copy(id = userId))
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun logout() {
        userPreferences.clearSession()
        _isLoggedIn.value = false
        _authState.value = AuthState.Idle
    }

    fun getCurrentUserId(): Long {
        return userPreferences.getUserId()
    }

    fun getCurrentUserRole(): String? {
        return userPreferences.getUserRole()
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}


