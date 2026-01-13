# 🚀 Flutter Progressly - Quick Start Guide

## Prerequisites

Before you begin, ensure you have:
- Flutter SDK (3.0.0 or higher)
- Dart SDK (comes with Flutter)
- Android Studio, Xcode, or Visual Studio Code
- A device or emulator for testing

## Installation Steps

### 1. Install Flutter

**macOS/Linux:**
```bash
git clone https://github.com/flutter/flutter.git -b stable
export PATH="$PATH:`pwd`/flutter/bin"
flutter doctor
```

**Windows:**
```bash
# Download from https://flutter.dev/docs/get-started/install/windows
# Add Flutter to your PATH
flutter doctor
```

### 2. Clone/Navigate to Project

```bash
cd /workspaces/Progressly
```

### 3. Get Dependencies

```bash
flutter pub get
```

## Running the App

### Android
```bash
# List available devices
flutter devices

# Run on default device
flutter run

# Run with hot reload
flutter run

# Run on specific device
flutter run -d <device_id>
```

### iOS (macOS only)
```bash
# Pod installation
cd ios
pod install
cd ..

# Run the app
flutter run

# Run on specific iOS device
flutter run -d <device_id>
```

### Web (Experimental)
```bash
flutter run -d chrome
```

## First Time Setup

When you first open the app:

1. **Name Screen**: Enter your name
2. **Age Screen**: Enter your age (15-100)
3. **Gender Screen**: Select your gender (affects water goal calculation)
4. **Weight Screen**: Enter your weight in kg (optional, but recommended)
5. **Notifications Screen**: Enable/disable notifications

After completing onboarding, you'll see the main home screen with 5 tabs:
- **Tasks**: Create and track tasks
- **Water**: Log water intake
- **Meals**: Log meals and calories
- **Habits**: Build and track habits
- **Profile**: View stats and settings

## Development Commands

### Hot Reload (recommended during development)
Press `r` in the terminal where `flutter run` is active to hot reload the app (preserves app state).

### Hot Restart
Press `R` in the terminal to hot restart (restarts the app, clears state).

### Build APK (Android Release)
```bash
flutter build apk --release
# Output: build/app/outputs/flutter-apk/app-release.apk
```

### Build AAB (Android App Bundle)
```bash
flutter build appbundle --release
# Output: build/app/outputs/bundle/release/app-release.aab
```

### Build iOS Release
```bash
flutter build ios --release
# Output: build/ios/iphoneos/Runner.app
```

### Run Tests
```bash
flutter test
```

### Analyze Code
```bash
flutter analyze
```

### Clean Build
```bash
flutter clean
flutter pub get
flutter run
```

## Troubleshooting

### "flutter: command not found"
Add Flutter to your PATH. See installation steps above.

### "Android SDK not found"
```bash
flutter config --android-sdk /path/to/android/sdk
```

### "iOS deployment target is too low"
Edit `ios/Podfile` and change the deployment target:
```ruby
platform :ios, '11.0'
```

### "Permission denied for notifications"
This is normal. Grant notification permissions when prompted on your device.

### "Database locked" error
This might occur if multiple app instances are running. Restart the app.

### "State not saved after hot reload"
Use `flutter run -t lib/main.dart` to run from scratch instead of hot reload.

## Project Structure Overview

```
lib/
├── main.dart                 # App entry point
├── config/
│   └── theme/              # Theme configuration
├── data/
│   ├── models/             # Data models
│   ├── database/           # SQLite and DAOs
│   └── repositories/       # Repository pattern
├── services/               # Services (storage, notifications)
└── features/
    ├── onboarding/         # Onboarding screens
    ├── home/               # Main app screens
    └── viewmodels/         # State management
```

## Common Tasks

### Adding a New Task
1. On Tasks screen, tap the **+** button
2. Enter task title and description
3. Tap "Add"
4. Tap the checkbox to complete the task

### Logging Water
1. On Water screen, use quick-add buttons (250ml, 500ml, 750ml)
2. Or tap **"Custom"** button and enter custom amount
3. Water goal is automatically calculated based on your weight and gender

### Logging a Meal
1. On Meals screen, tap the **+** button
2. Enter meal name, calories, and type
3. Tap "Log"

### Creating a Habit
1. On Habits screen, tap the **+** button
2. Enter habit name, description, and frequency
3. Tap "Add"
4. Tap the checkbox each day to mark as complete

### Viewing Statistics
1. Go to Profile screen
2. See today's statistics for all activities
3. View your daily summary

## Performance Tips

1. **Enable Android VM Acceleration** (for faster emulator)
   - On Windows: Use Android Studio's AVD Manager with Intel HAXM
   - On Mac: Use Android Studio's AVD Manager

2. **Use Release Build for Testing**
   ```bash
   flutter run --release
   ```

3. **Monitor Performance**
   ```bash
   flutter run --profile
   ```

## Environment Variables (Optional)

Create a `.env` file in the project root:
```
ANDROID_HOME=/path/to/android/sdk
JAVA_HOME=/path/to/java
```

## Next Steps

1. ✅ Run the app and complete onboarding
2. ✅ Try all the features
3. ✅ Check the database by inspecting local data
4. ✅ Modify the code and use hot reload
5. ✅ Build a release version
6. ✅ Share feedback!

## Resources

- [Flutter Documentation](https://flutter.dev/docs)
- [Dart Documentation](https://dart.dev/guides)
- [GetX Documentation](https://github.com/jonataslaw/getx)
- [SQLite Documentation](https://www.sqlite.org/docs.html)
- [Material Design 3](https://m3.material.io/)

## Support

For issues:
1. Run `flutter doctor` to check your environment
2. Clean and rebuild: `flutter clean && flutter pub get`
3. Check the console output for error messages
4. Search [Flutter issues](https://github.com/flutter/flutter/issues)

---

Happy coding! 🚀 Enjoy building with Progressly! 📊
