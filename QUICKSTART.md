# 🚀 Quick Start Guide - Progressly

Get Progressly up and running in 5 minutes!

## Installation Steps

### Option 1: Android Studio (Recommended)

1. **Open Android Studio**
   - Click "Open" → Select the Progressly folder
   - Wait for Gradle to sync automatically

2. **Create Virtual Device** (if needed)
   - Tools → Device Manager
   - Click "Create device"
   - Select "Pixel 6" or any device with Android 8.0+
   - Start the device

3. **Build & Run**
   - Click "Run" (green play icon) in the toolbar
   - Select your device/emulator
   - Wait for app to build and launch

4. **Complete Onboarding**
   - Enter your name
   - Select your age
   - Choose your gender
   - Enter weight (optional)
   - Enable or skip notifications
   - Tap "Get Started"

### Option 2: Command Line

```bash
# Clone repository
git clone https://github.com/mdmahbubreza/Progressly.git
cd Progressly

# Build debug APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Run app
adb shell am start -n com.progressly.app/.ui.MainActivity
```

### Option 3: Direct APK Installation

1. Download APK from [Releases](https://github.com/mdmahbubreza/Progressly/releases)
2. Transfer to device: `adb push app-release.apk /sdcard/`
3. Install: `adb install /sdcard/app-release.apk`
4. Open app from launcher

## First Time Setup

### Complete Onboarding (1 minute)
1. **Name** - Enter any name (required)
2. **Age** - Slide to select age 15-100
3. **Gender** - Choose Male, Female, or Other
4. **Weight** - Enter weight for water goal calculation (optional, can skip)
5. **Notifications** - Allow or deny (can be changed later in settings)

### Initial Configuration (Optional)
Once in the app, you can:
- Set daily water goal (calculated automatically)
- Configure notification times
- Customize themes (currently dark mode is default)

## Using Progressly

### Add Your First Task (30 seconds)
1. Go to **Tasks** tab
2. Type task name
3. Tap **+** button
4. Tap ✓ to complete
5. Tap **X** to delete

### Log Your First Water (15 seconds)
1. Go to **Water** tab
2. Tap **250ml**, **500ml**, or **750ml** quick buttons
3. OR enter custom amount and tap **+**
4. Watch progress bar fill up!

### Add Your First Meal (30 seconds)
1. Go to **Meals** tab
2. Type meal name
3. Enter calories (optional)
4. Tap **+** to log
5. See daily calorie total update

### Create Your First Habit (30 seconds)
1. Go to **Habits** tab
2. Type habit name
3. Tap **+** to create
4. Tap **✓** button to mark complete daily
5. Watch streak grow!

### Check Your Progress (15 seconds)
1. Go to **Profile** tab
2. See daily summary cards
3. View all active streaks
4. Check nutrition stats

## Features Breakdown

| Feature | Location | How to Use |
|---------|----------|-----------|
| **Tasks** | Bottom Nav | Add → Complete → Delete |
| **Water** | Bottom Nav | Quick-add or custom amount |
| **Meals** | Bottom Nav | Enter name + calories |
| **Habits** | Bottom Nav | Create → Mark complete daily |
| **Profile** | Bottom Nav | View all stats & streaks |
| **Streaks** | Each section | Maintained automatically |
| **Dark Mode** | System | Enabled by default |

## Keyboard Shortcuts (Android)

- **Back** - Return to previous screen
- **Recent** - Switch between sections
- **Volume Up/Down** - Adjust screen brightness (long press)

## Tips & Tricks

1. **Water Goals** - App automatically calculates based on your weight
   - Example: 70kg person needs ~2,450ml/day

2. **Streaks Reset** - Miss one day = streak goes to 0
   - Log your activities daily to maintain streaks!

3. **Quick Add** - Use the **+** buttons for fastest logging

4. **Dark Mode** - Enabled by default for better battery life
   - Can be changed in settings (Android system settings)

5. **No Internet Needed** - App works 100% offline
   - All data stored locally on your device

## Troubleshooting

### App Won't Open
- **Solution**: Clear app data and try again
  ```bash
  adb shell pm clear com.progressly.app
  ```

### Notifications Not Showing
- **Solution**: Check notification permissions
  - Settings → Apps → Progressly → Permissions → Notifications
  - Enable "Allow notifications"

### Data Not Saving
- **Solution**: Check available storage
  - App needs at least 50MB free space

### Crashes on Startup
- **Solution**: Reinstall the app
  ```bash
  adb uninstall com.progressly.app
  adb install app-release.apk
  ```

## Next Steps

After setup, explore:
1. **Daily Tracking** - Log activities every day
2. **Build Streaks** - Try to maintain 7-day streaks
3. **Check Profile** - Monitor your progress weekly
4. **Adjust Goals** - Customize water goal based on activity

## Need Help?

- 📖 Read [README.md](README.md) for detailed features
- 👨‍💻 Check [DEVELOPMENT.md](DEVELOPMENT.md) for technical info
- 🐛 Report issues on GitHub
- 💬 Share feedback and suggestions

## System Requirements

- ✅ Android 8.0+ (API 26)
- ✅ 50MB free storage
- ✅ 100MB RAM minimum
- ✅ No internet connection needed

---

**You're all set! Start tracking your progress today! 🎯**
