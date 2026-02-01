# 🏥 Health Care Management System

## Overview
A comprehensive native Android application built with Jetpack Compose and Kotlin for managing healthcare services. The app supports two user roles: **Patients** and **Doctors**, enabling seamless appointment booking, medical record management, and medication reminders.

## Features

### For Patients 👤
| Feature | Description | Screen |
|---------|-------------|--------|
| **Register** | Create account with personal details | RegisterScreen |
| **Login** | Secure authentication | LoginScreen |
| **Dashboard** | Main menu with all features | PatientHomeScreen |
| **Book Appointment** | Select doctor and schedule | BookAppointmentScreen |
| **View Appointments** | Track all bookings and status | AppointmentsListScreen |
| **Medical Records** | Access health history | MedicalRecordsScreen |
| **Medication Reminders** | Manage medicine schedules | MedicationRemindersScreen |
| **Add Reminder** | Create new medication alert | AddReminderScreen |
| **Health Tips** | Read wellness advice | HealthTipsScreen |

### For Doctors 👨‍⚕️
| Feature | Description | Screen |
|---------|-------------|--------|
| **Register** | Create doctor account | RegisterScreen |
| **Login** | Secure authentication | LoginScreen |
| **Dashboard** | Doctor control panel | DoctorHomeScreen |
| **View Appointments** | See all bookings (Pending/All) | DoctorAppointmentsScreen |
| **Approve/Reject** | Manage appointment requests | DoctorAppointmentsScreen |
| **Mark Complete** | Update appointment status | DoctorAppointmentsScreen |
| **Add Medical Record** | Document patient consultation | AddMedicalRecordScreen |
| **Health Tips** | Access wellness information | HealthTipsScreen |

## Technology Stack

```
📱 UI Framework:     Jetpack Compose (Material 3)
💾 Database:         Room (SQLite)
🧭 Navigation:       Navigation Compose
⚡ Async:            Kotlin Coroutines + Flow
🏗️ Architecture:     MVVM Pattern
📦 DI:               Manual with Repositories
⏰ Scheduling:       WorkManager
🔔 Notifications:    NotificationManager
💾 Storage:          SharedPreferences
🎨 Design:           Material Design 3
```

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

### 📊 Tables
1. **users**: Stores patient and doctor information
2. **appointments**: Manages appointment bookings
3. **medical_records**: Stores patient medical history
4. **medication_reminders**: Manages medication schedules

## Setup Instructions

### 📋 Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK API 26 or higher

### 🔧 Installation
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

### 🚀 First Run
1. Launch the app
2. Create a new account (Patient or Doctor)
3. Login with your credentials

## User Flow

### 👤 For Patients:
1. Register and log in
2. Browse available doctors
3. Book an appointment
4. Receive appointment approval from doctor
5. View medical records after consultation
6. Set medication reminders

### 👨‍⚕️ For Doctors:
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

### 🔐 Authentication
- Local authentication using Room Database
- Session management with SharedPreferences
- Password storage (Note: In production, use encryption)

### 🔔 Notifications
- WorkManager for scheduled medication reminders
- NotificationChannel for Android O and above
- Customizable reminder times

### 💾 Database
- Room Database for offline data persistence
- Foreign key relationships between entities
- Flow-based reactive data updates

## 🔄 State Management

### AuthViewModel States
- `Idle` - Initial state
- `Loading` - Processing request
- `Success(user)` - Authentication successful
- `Error(message)` - Authentication failed

### AppointmentViewModel States
- `Idle` - Initial state
- `Loading` - Processing appointment
- `Success(message)` - Operation successful
- `Error(message)` - Operation failed

## Security Considerations
- Passwords should be hashed (implement BCrypt or similar)
- Add data encryption for sensitive information
- Implement proper session timeout
- Add two-factor authentication


