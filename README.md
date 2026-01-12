# 📊 Progressly - Offline-First Productivity & Wellness Tracker

A modern, clean, and fully functional Android app that helps users track progress, build habits, stay hydrated, stay productive, and stay motivated. Built with **Kotlin**, **MVVM Architecture**, **Material Design 3**, and **Room Database** for offline-first functionality.

## ✨ Features

### 🎯 **5 Main Sections**
1. **Tasks** - Track productivity and complete tasks
2. **Meals** - Log meals and track calories
3. **Water** - Smart hydration tracking with goal calculation
4. **Habits** - Build consistency with daily habits
5. **Profile** - View progress, stats, and streaks

### 🔥 **Gamification: Streak System**
- Daily streaks for all tracking categories
- Weekly streak tracking
- Longest streak records
- Streaks reset if you miss a day
- Visual progress indicators

### 🚀 **Onboarding Flow**
- User name setup
- Age selection
- Gender selection
- Weight input (optional, for water goal calculation)
- Notification permissions

### 💧 **Smart Water Goal System**
- Automatically calculates daily water intake based on:
  - Age
  - Gender
  - Weight
  - Formula: `weight * 35 ml` with gender adjustments
- Quick-add water buttons (250ml, 500ml, 750ml)
- Custom amount logging
- Progress visualization

### 🎨 **Modern UI/UX**
- Material Design 3 components
- Dark Mode support (enabled by default)
- Smooth animations and transitions
- Clean typography
- Modern icon usage
- Minimal, clutter-free design

### 📱 **Offline-First Architecture**
- No internet required
- All data stored locally using Room Database
- SharedPreferences for user settings
- Local notifications
- Complete data persistence

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database
- **Preferences**: SharedPreferences
- **UI Framework**: AndroidX & Material Design 3
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

### Key Libraries
- **Navigation**: AndroidX Navigation
- **Lifecycle**: Lifecycle components & LiveData
- **Concurrency**: Kotlin Coroutines
- **Database**: Room with Type Converters
- **UI Components**: Material Design 3
- **Notifications**: Work Manager
- **Charts**: MPAndroidChart

## 📁 Project Structure

```
app/
├── src/main/java/com/progressly/app/
│   ├── ProgresslyApplication.kt          # App initialization & theme setup
│   ├── ui/
│   │   ├── MainActivity.kt                # Main activity with bottom nav
│   │   ├── onboarding/
│   │   │   ├── OnboardingActivity.kt
│   │   │   └── fragments/
│   │   │       ├── OnboardingNameFragment.kt
│   │   │       ├── OnboardingAgeFragment.kt
│   │   │       ├── OnboardingGenderFragment.kt
│   │   │       ├── OnboardingWeightFragment.kt
│   │   │       └── OnboardingNotificationsFragment.kt
│   │   ├── fragments/
│   │   │   ├── TasksFragment.kt
│   │   │   ├── WaterFragment.kt
│   │   │   ├── MealsFragment.kt
│   │   │   ├── HabitsFragment.kt
│   │   │   └── ProfileFragment.kt
│   │   ├── adapters/
│   │   │   └── Adapters.kt               # All RecyclerView adapters
│   │   └── viewmodels/
│   │       ├── TaskViewModel.kt
│   │       ├── WaterViewModel.kt
│   │       ├── MealViewModel.kt
│   │       ├── HabitViewModel.kt
│   │       └── ProfileViewModel.kt
│   ├── data/
│   │   ├── database/
│   │   │   ├── ProgresslyDatabase.kt
│   │   │   ├── dao/
│   │   │   │   └── Daos.kt              # All Data Access Objects
│   │   │   └── entities/
│   │   │       └── Entities.kt          # Database entities
│   │   ├── models/
│   │   │   └── Models.kt                # Data models
│   │   ├── preferences/
│   │   │   └── PreferencesManager.kt    # SharedPreferences wrapper
│   │   └── repositories/
│   │       └── Repositories.kt          # Repository layer
│   └── utils/
│       ├── LocalDateConverter.kt        # Room type converter
│       └── NotificationManager.kt       # Local notifications
│
├── res/
│   ├── layout/                          # XML layout files
│   ├── drawable/                        # Vector drawables
│   ├── values/                          # Resources
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   ├── styles.xml
│   │   ├── dimens.xml
│   │   └── themes
│   ├── values-night/                    # Dark mode resources
│   ├── mipmap-*/                        # App icons
│   └── xml/                             # System resources
│
└── AndroidManifest.xml
```

## 🏗️ Architecture Overview

### MVVM Architecture
```
View (Fragment/Activity)
   ↓
ViewModel (Business Logic)
   ↓
Repository (Data Access)
   ↓
Database (Room) + Preferences
```

### Data Flow
1. **Fragments** display UI and handle user interactions
2. **ViewModels** manage business logic and state
3. **Repositories** provide data access abstraction
4. **DAOs** handle database queries
5. **Room Database** stores all data locally
6. **SharedPreferences** stores user settings

## 🗄️ Database Schema

### Tasks
- `TaskEntity` - Task information
- `TaskStreakEntity` - Streak tracking

### Meals
- `MealEntity` - Meal logs
- `MealStreakEntity` - Meal logging streaks

### Water
- `WaterLogEntity` - Water intake logs
- `WaterStreakEntity` - Water drinking streaks

### Habits
- `HabitEntity` - Habit definitions
- `HabitLogEntity` - Habit completion logs
- `HabitStreakEntity` - Habit completion streaks

## 🎨 Material Design 3 Theme

### Color Palette
- **Primary**: #6750A4 (Purple)
- **Secondary**: #625B71 (Brown)
- **Tertiary**: #7D5260 (Rose)
- **Dark Mode**: Full dark theme with optimized colors

### Component Styling
- Material Buttons with ripple effects
- FloatingActionButtons with tint
- Material TextInputLayouts
- CardView for content containers
- ProgressBar for goal tracking
- RecyclerViews with smooth animations

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- Android SDK 26+ (minimum)
- JDK 17+
- Gradle 8.0+

### Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/mdmahbubreza/Progressly.git
   cd Progressly
   ```

2. **Open in Android Studio**
   - File → Open → Select project folder
   - Let Gradle sync complete

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on emulator/device**
   - Select target device
   - Press Run (Shift + F10) or click Run button
   - App will launch with onboarding flow

### First Time Setup
1. Enter your name
2. Select your age (15-100)
3. Choose your gender (Male/Female/Other)
4. (Optional) Enter your weight for water goal calculation
5. Choose notification preferences
6. Start tracking!

## 📊 Usage Guide

### Tasks Section
- **Add Task**: Type task name and tap + button
- **Complete Task**: Check the checkbox
- **Delete Task**: Tap X button
- **View Stats**: See daily completed count at top

### Water Section
- **Quick Log**: Tap 250ml, 500ml, or 750ml buttons
- **Custom Amount**: Enter amount in ml and tap +
- **Track Progress**: Visual progress bar shows goal completion
- **Check Streaks**: View daily and longest streak

### Meals Section
- **Log Meal**: Enter meal name and calories, tap +
- **Delete Entry**: Tap X button
- **View Nutrition**: See daily calorie count and meal count

### Habits Section
- **Create Habit**: Enter habit name, tap +
- **Complete Habit**: Tap checkmark button
- **Delete Habit**: Tap X button
- **Track Completion**: See daily completion count

### Profile Section
- **Daily Summary**: View all daily stats at a glance
- **Streaks**: See current streaks for all categories
- **Nutrition**: View total calories consumed
- **Statistics**: Track progress over time

## 🔔 Notifications

The app supports local notifications for:
- **Water Reminders**: Scheduled daily reminders to drink water
- **Habit Reminders**: Custom habit reminder times
- **Task Reminders**: Upcoming task notifications
- **Achievements**: Streak milestone notifications

Notification permissions are requested during onboarding.

## 🎯 Key Features Explained

### Smart Water Goal Calculation
```kotlin
val dailyGoal = weight * 35 ml
// Adjusted by 5% lower for females
```

Example: 70kg person
- Male: 2,450 ml/day
- Female: 2,327 ml/day

### Streak System
- **Daily Streak**: Counts consecutive days of activity
- **Reset**: Resets to 0 if you miss a day
- **Longest Streak**: Historical best streak
- **Weekly Streak**: Activity count in current week

### Onboarding Persistence
- Onboarding shown only once on first app launch
- User data stored in SharedPreferences
- Settings persist across app restarts

## 🛡️ Data Privacy & Security
- ✅ All data stored locally on device
- ✅ No internet connection required
- ✅ No external API calls
- ✅ No user data tracking
- ✅ No advertisements
- ✅ Full app backup support

## 📱 Supported Features by Android Version
- **Android 8.0+ (API 26)**: Core functionality
- **Android 9.0+ (API 28)**: Enhanced notifications
- **Android 10.0+ (API 29)**: Gesture navigation support
- **Android 13.0+ (API 33)**: Notification runtime permissions
- **Android 14.0 (API 34)**: Full Material You support

## 🐛 Debugging & Testing

### View Database
- Android Studio → Device File Explorer → data/com.progressly.app/databases/

### Clear App Data
- Settings → Apps → Progressly → Clear Data/Cache

### Enable Verbose Logging
```kotlin
// Add in ProgresslyApplication.kt
Log.v("Progressly", "Debug message")
```

## 📈 Performance Optimization
- Lazy loading with RecyclerViews
- Efficient database queries with indexes
- Coroutines for non-blocking operations
- ViewBinding for compile-time safe layouts
- Proguard obfuscation for release builds

## 🔄 Future Enhancements
- [ ] Weekly/monthly statistics charts
- [ ] Custom habit frequencies
- [ ] Meal planning templates
- [ ] Export data to CSV
- [ ] Cloud sync option
- [ ] Widget support
- [ ] Companion wear app
- [ ] Voice logging

## 📄 License
This project is open source and available under the MIT License.

## 👨‍💻 Author
Created by **Mahbub Reza** (@mdmahbubreza)

## 🤝 Contributing
Contributions are welcome! Please follow the MVVM architecture and Material Design guidelines.

## 📞 Support
For issues, feature requests, or questions, please open an issue on GitHub.

---

**Built with ❤️ for productivity and wellness tracking**