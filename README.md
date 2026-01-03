# 🏥 Health Care Management System

## Overview
A comprehensive native Android application built with Jetpack Compose and Kotlin for managing healthcare services. The app supports two user roles: **Patients** and **Doctors**, enabling seamless appointment booking, medical record management, and medication reminders.

## Features

### 👤 Patient Features
- **Registration & Login**: Secure authentication with local database storage using Room
- **Appointment Booking**: Book appointments with available doctors
- **Appointment Management**: View appointment status (Pending/Approved/Completed)
- **Medical Records**: Access and view health records, prescriptions, and past visits
- **Medication Reminders**: Set and manage medication schedules with notifications
- **Health Tips**: Access general health and wellness information

### 👨‍⚕️ Doctor Features
- **Registration & Login**: Separate portal for doctor authentication
- **Appointment Management**: 
  - View pending appointment requests
  - Approve or reject appointments
  - Mark appointments as completed
- **Patient Record Management**: Add and update medical diagnoses, prescriptions, and treatment notes

## Technology Stack

### Core Technologies
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database (SQLite)
- **Navigation**: Navigation Compose
- **Background Tasks**: WorkManager
- **Dependency Injection**: Manual DI with Repository Pattern

### Key Dependencies
- AndroidX Core KTX
- Jetpack Compose (Material3)
- Room Database
- Navigation Compose
- WorkManager
- ViewModel Compose
- Gson

## Project Structure

```
app/src/main/java/com/example/healthcaremanager/
├── data/
│   ├── entity/          # Database entities (User, Appointment, MedicalRecord, etc.)
│   ├── dao/             # Data Access Objects
│   ├── repository/      # Repository layer
│   ├── Converters.kt    # Type converters for Room
│   ├── HealthCareDatabase.kt
│   └── UserPreferences.kt
├── viewmodel/           # ViewModels for UI logic
│   ├── AuthViewModel.kt
│   ├── AppointmentViewModel.kt
│   ├── MedicalRecordViewModel.kt
│   └── MedicationReminderViewModel.kt
├── ui/
│   ├── screens/         # Composable screens
│   └── theme/           # App theming
├── navigation/          # Navigation setup
│   ├── Screen.kt
│   └── AppNavigation.kt
├── worker/              # Background workers
│   └── MedicationReminderWorker.kt
└── MainActivity.kt
```

## Database Schema

### Tables
1. **users**: Stores patient and doctor information
2. **appointments**: Manages appointment bookings
3. **medical_records**: Stores patient medical history
4. **medication_reminders**: Manages medication schedules

## Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK API 26 or higher

### Installation
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

### First Run
1. Launch the app
2. Create a new account (Patient or Doctor)
3. Login with your credentials

## User Flow

### For Patients:
1. Register and log in
2. Browse available doctors
3. Book an appointment
4. Receive appointment approval from doctor
5. View medical records after consultation
6. Set medication reminders

### For Doctors:
1. Register and log in as a doctor
2. View pending appointment requests
3. Approve/reject appointments
4. Mark appointments as completed
5. Add medical records for patients

## Permissions Required
- `POST_NOTIFICATIONS`: For medication reminder notifications
- `SCHEDULE_EXACT_ALARM`: For precise medication reminders
- `USE_EXACT_ALARM`: For alarm functionality

## Key Features Implementation

### Authentication
- Local authentication using Room Database
- Session management with SharedPreferences
- Password storage (Note: In production, use encryption)

### Notifications
- WorkManager for scheduled medication reminders
- NotificationChannel for Android O and above
- Customizable reminder times

### Database
- Room Database for offline data persistence
- Foreign key relationships between entities
- Flow-based reactive data updates

## Future Enhancements
- [ ] Firebase integration for cloud sync
- [ ] Video consultation feature
- [ ] PDF export for medical records
- [ ] Multi-language support
- [ ] Dark mode
- [ ] Push notifications for appointment updates
- [ ] Integration with health tracking devices

## Security Considerations
- Passwords should be hashed (implement BCrypt or similar)
- Add data encryption for sensitive information
- Implement proper session timeout
- Add two-factor authentication

## License
This project is for educational purposes.

## Contributors
Developed as a course assignment for Healthcare Management System.

## Support
For issues or questions, please create an issue in the repository.

