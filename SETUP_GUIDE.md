# Setup and Build Instructions

## Prerequisites
Before building the project, ensure you have:
- ✅ Android Studio (latest version recommended)
- ✅ JDK 11 or higher installed
- ✅ Android SDK installed (API 26+)

## Step-by-Step Setup

### 1. Configure Java/JDK in Android Studio
1. Open Android Studio
2. Go to **File → Project Structure → SDK Location**
3. Note your JDK location
4. If not set, click **Download JDK** or point to existing JDK installation

### 2. Open the Project
1. Launch Android Studio
2. Click **File → Open**
3. Navigate to: `C:\Users\olart\AndroidStudioProjects\HealthCareManager`
4. Click **OK**

### 3. Sync Gradle Files
1. Android Studio will automatically prompt to sync Gradle
2. Click **Sync Now** in the banner at the top
3. Wait for the sync to complete (this will download all dependencies)

### 4. Resolve Any Issues
If you encounter errors:
- **Build errors**: Go to **Build → Clean Project**, then **Build → Rebuild Project**
- **SDK errors**: Go to **Tools → SDK Manager** and install required SDK versions
- **Gradle errors**: Click **File → Invalidate Caches / Restart**

### 5. Run the Application

#### Option A: Using Android Emulator
1. Click **Tools → Device Manager**
2. Create a new Virtual Device (if you don't have one)
   - Choose a device (e.g., Pixel 5)
   - Select API Level 26 or higher
   - Download system image if needed
3. Click the **Run** button (▶️) or press `Shift + F10`
4. Select your emulator from the list

#### Option B: Using Physical Device
1. Enable Developer Options on your Android device
2. Enable USB Debugging
3. Connect device via USB
4. Select your device from the device dropdown
5. Click **Run** (▶️)

## Testing the Application

### Test Patient Flow:
1. **Register as Patient**
   - Click "Register"
   - Fill in details
   - Select "PATIENT" role
   - Click "Register"

2. **Book Appointment**
   - From Patient Home, click "Book Appointment"
   - Select a doctor
   - Choose date/time
   - Add notes (optional)
   - Click "Book Appointment"

3. **View Appointments**
   - Click "My Appointments"
   - Check appointment status

4. **Add Medication Reminder**
   - Click "Medication Reminders"
   - Click the (+) button
   - Fill in medication details
   - Set time
   - Click "Add Reminder"

5. **View Medical Records**
   - Click "Medical Records"
   - View your health history

6. **Read Health Tips**
   - Click "Health Tips"
   - Browse wellness information

### Test Doctor Flow:
1. **Register as Doctor**
   - Logout from patient account
   - Click "Register"
   - Select "DOCTOR" role
   - Complete registration

2. **Manage Appointments**
   - Login as doctor
   - Click "Appointments"
   - View "Pending" tab
   - Approve or Reject requests

3. **Add Medical Records**
   - After approving, mark as "Complete"
   - Click "Add Record"
   - Enter diagnosis, prescription, treatment notes
   - Click "Save Medical Record"

## Troubleshooting

### Common Issues:

**Issue: "JAVA_HOME is not set"**
- Solution: Set JAVA_HOME in Android Studio settings
- Go to **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**
- Set Gradle JDK to the installed JDK version

**Issue: "SDK location not found"**
- Solution: Download Android SDK through SDK Manager
- Go to **Tools → SDK Manager**
- Install Android 8.0 (API 26) or higher

**Issue: "Unresolved reference" errors**
- Solution: Sync Gradle files
- Click **File → Sync Project with Gradle Files**

**Issue: Build fails with dependency errors**
- Solution: Clear cache and rebuild
- **File → Invalidate Caches / Restart**
- After restart: **Build → Clean Project**
- Then: **Build → Rebuild Project**

**Issue: App crashes on startup**
- Check Logcat for error messages
- Ensure minimum SDK version matches device
- Verify all permissions are granted

## Project Structure Verification

After opening, you should see these folders:
```
app/
├── src/main/
│   ├── java/com/example/healthcaremanager/
│   │   ├── data/          (Database entities, DAOs, repositories)
│   │   ├── viewmodel/     (ViewModels)
│   │   ├── ui/screens/    (Compose screens)
│   │   ├── navigation/    (Navigation setup)
│   │   ├── worker/        (Background workers)
│   │   └── MainActivity.kt
│   ├── res/               (Resources)
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Build Configuration

The project uses:
- **Gradle Version**: 8.13.2
- **Kotlin Version**: 2.0.21
- **Compose Version**: 2024.09.00
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 36

## Additional Notes

- First build may take longer as Gradle downloads dependencies
- Emulator requires hardware acceleration (Intel HAXM or AMD equivalent)
- Physical device testing requires Developer Mode enabled
- Notification testing requires Android 8.0+ for notification channels

## Getting Help

If you encounter issues not covered here:
1. Check Android Studio's **Build** output window for detailed errors
2. Review **Logcat** for runtime errors
3. Verify all files were created correctly
4. Ensure Android Studio is up to date

## Success Indicators

You'll know the setup is successful when:
✅ Gradle sync completes without errors
✅ Build succeeds (green checkmark)
✅ App launches on emulator/device
✅ Login screen appears
✅ You can register and login

---

**Ready to Go!** 🚀

Once setup is complete, you have a fully functional Health Care Management System with:
- Patient appointment booking
- Doctor appointment management
- Medical records
- Medication reminders
- Health tips

Enjoy using the app!

