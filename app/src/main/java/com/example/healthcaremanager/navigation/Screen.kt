package com.example.healthcaremanager.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object PatientHome : Screen("patient_home")
    object DoctorHome : Screen("doctor_home")
    object BookAppointment : Screen("book_appointment")
    object AppointmentsList : Screen("appointments_list")
    object MedicalRecords : Screen("medical_records")
    object MedicationReminders : Screen("medication_reminders")
    object AddReminder : Screen("add_reminder")
    object DoctorAppointments : Screen("doctor_appointments")
    object AddMedicalRecord : Screen("add_medical_record/{appointmentId}") {
        fun createRoute(appointmentId: Long) = "add_medical_record/$appointmentId"
    }
    object HealthTips : Screen("health_tips")
}


