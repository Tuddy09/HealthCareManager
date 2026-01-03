package com.example.healthcaremanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.healthcaremanager.ui.screens.*
import com.example.healthcaremanager.viewmodel.AuthViewModel

@Composable
fun AppNavigation(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val userRole = authViewModel.getCurrentUserRole()

    val startDestination = when {
        !isLoggedIn -> Screen.Login.route
        userRole == "PATIENT" -> Screen.PatientHome.route
        userRole == "DOCTOR" -> Screen.DoctorHome.route
        else -> Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.PatientHome.route) {
            PatientHomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.DoctorHome.route) {
            DoctorHomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.BookAppointment.route) {
            BookAppointmentScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.AppointmentsList.route) {
            AppointmentsListScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.MedicalRecords.route) {
            MedicalRecordsScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.MedicationReminders.route) {
            MedicationRemindersScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.AddReminder.route) {
            AddReminderScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.DoctorAppointments.route) {
            DoctorAppointmentsScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.AddMedicalRecord.route,
            arguments = listOf(navArgument("appointmentId") { type = NavType.LongType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getLong("appointmentId") ?: 0L
            AddMedicalRecordScreen(
                navController = navController,
                authViewModel = authViewModel,
                appointmentId = appointmentId
            )
        }

        composable(Screen.HealthTips.route) {
            HealthTipsScreen(navController = navController)
        }
    }
}

