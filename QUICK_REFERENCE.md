# Health Care Manager - Quick Reference

## 📱 Application Overview

A complete native Android healthcare management application with separate interfaces for Patients and Doctors.

## 🎯 Key Features at a Glance

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

## 🗂️ File Structure

### Database Layer
```
data/
├── entity/
│   ├── User.kt                    (Patient/Doctor profiles)
│   ├── Appointment.kt             (Booking information)
│   ├── MedicalRecord.kt           (Patient health records)
│   └── MedicationReminder.kt      (Reminder data)
├── dao/
│   ├── UserDao.kt                 (User queries)
│   ├── AppointmentDao.kt          (Appointment queries)
│   ├── MedicalRecordDao.kt        (Record queries)
│   └── MedicationReminderDao.kt   (Reminder queries)
├── repository/
│   ├── UserRepository.kt
│   ├── AppointmentRepository.kt
│   ├── MedicalRecordRepository.kt
│   └── MedicationReminderRepository.kt
├── HealthCareDatabase.kt          (Room database)
├── Converters.kt                  (Type converters)
└── UserPreferences.kt             (Session storage)
```

### ViewModel Layer
```
viewmodel/
├── AuthViewModel.kt               (Login/Register logic)
├── AppointmentViewModel.kt        (Appointment management)
├── MedicalRecordViewModel.kt      (Record operations)
└── MedicationReminderViewModel.kt (Reminder scheduling)
```

### UI Layer
```
ui/screens/
├── LoginScreen.kt
├── RegisterScreen.kt
├── PatientHomeScreen.kt
├── DoctorHomeScreen.kt
├── BookAppointmentScreen.kt
├── AppointmentsListScreen.kt
├── MedicalRecordsScreen.kt
├── MedicationRemindersScreen.kt
├── AddReminderScreen.kt
├── DoctorAppointmentsScreen.kt
├── AddMedicalRecordScreen.kt
└── HealthTipsScreen.kt
```

### Navigation
```
navigation/
├── Screen.kt                      (Route definitions)
└── AppNavigation.kt               (Navigation graph)
```

### Background Tasks
```
worker/
└── MedicationReminderWorker.kt    (Notification worker)
```

## 🎨 UI Components Used

### Material 3 Components
- `Scaffold` - Screen structure
- `TopAppBar` - Navigation bar
- `Card` - Content containers
- `Button` / `OutlinedButton` - Actions
- `TextField` / `OutlinedTextField` - Input fields
- `LazyColumn` - Scrollable lists
- `LazyVerticalGrid` - Grid layouts
- `FloatingActionButton` - Quick actions
- `Switch` - Toggle controls
- `Tab` / `TabRow` - Tab navigation
- `ExposedDropdownMenu` - Dropdown selection
- `CircularProgressIndicator` - Loading states
- `Icon` - Visual indicators

## 📊 Data Models

### User
- id, name, email, password, age, gender, contact, role

### Appointment
- id, patientId, doctorId, dateTime, status, notes

### MedicalRecord
- id, patientId, doctorId, appointmentId, date, diagnosis, prescription, treatmentNotes

### MedicationReminder
- id, patientId, medicationName, dosage, frequency, timeInMillis, isActive

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

### Similar patterns for MedicalRecordViewModel and MedicationReminderViewModel

## 🔔 Notification System

### MedicationReminderWorker
- Scheduled using WorkManager
- Triggers at specified time
- Creates notification with medication details
- Uses notification channels for Android O+

## 🔐 Authentication Flow

```
1. App Launch
   ├─> Check UserPreferences
   ├─> If logged in → Navigate to Home (based on role)
   └─> If not logged in → Show Login Screen

2. Login/Register
   ├─> Validate credentials
   ├─> Save to database
   ├─> Store session in UserPreferences
   └─> Navigate to role-specific home

3. Logout
   ├─> Clear UserPreferences
   └─> Navigate to Login Screen
```

## 📝 Appointment Status Flow

```
Patient Books Appointment
    ↓
Status: PENDING
    ↓
Doctor Reviews → [Approve / Reject]
    ↓                    ↓
Status: APPROVED    Status: REJECTED
    ↓
Doctor Marks Complete
    ↓
Status: COMPLETED
    ↓
Doctor Adds Medical Record
```

## 🛠️ Development Tools

### Build Configuration
- **Gradle**: 8.13.2
- **Kotlin**: 2.0.21
- **KSP**: 2.0.21-1.0.28 (for Room)

### Key Libraries
- **Room**: 2.6.1
- **Navigation Compose**: 2.7.7
- **WorkManager**: 2.9.0
- **Compose BOM**: 2024.09.00
- **Gson**: 2.10.1

## 🎯 Testing Checklist

### Patient Tests
- [ ] Register new patient account
- [ ] Login with patient credentials
- [ ] View patient dashboard
- [ ] Book appointment with doctor
- [ ] View appointment in list
- [ ] Check appointment status
- [ ] View medical records (after doctor adds)
- [ ] Create medication reminder
- [ ] Toggle reminder on/off
- [ ] Delete reminder
- [ ] View health tips
- [ ] Logout

### Doctor Tests
- [ ] Register new doctor account
- [ ] Login with doctor credentials
- [ ] View doctor dashboard
- [ ] View pending appointments
- [ ] Approve appointment
- [ ] Reject appointment
- [ ] Mark appointment as complete
- [ ] Add medical record for patient
- [ ] View all appointments
- [ ] View health tips
- [ ] Logout

### System Tests
- [ ] Session persistence (close and reopen app)
- [ ] Multiple users (switch between accounts)
- [ ] Notification delivery (medication reminders)
- [ ] Data persistence (restart app, data intact)
- [ ] Error handling (invalid inputs)
- [ ] Loading states display correctly

## 📞 Support & Resources

### Documentation
- `README.md` - Project overview
- `IMPLEMENTATION_SUMMARY.md` - Complete feature list
- `SETUP_GUIDE.md` - Build and run instructions
- This file - Quick reference

### Useful Commands
- **Build Project**: `./gradlew build`
- **Clean Build**: `./gradlew clean build`
- **Install APK**: `./gradlew installDebug`
- **Run Tests**: `./gradlew test`

## 🚀 Quick Start

1. Open in Android Studio
2. Sync Gradle files
3. Run on emulator/device
4. Register as Patient
5. Register as Doctor (separate account)
6. Test appointment flow
7. Test medication reminders
8. Explore all features!

---

**Made with ❤️ using Kotlin & Jetpack Compose**

