# 🏗️ Architecture & Project Map - Progressly

## Application Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  MainActivity                    OnboardingActivity          │
│  ├── TasksFragment              ├── OnboardingNameFragment   │
│  ├── WaterFragment              ├── OnboardingAgeFragment    │
│  ├── MealsFragment              ├── OnboardingGenderFragment │
│  ├── HabitsFragment             ├── OnboardingWeightFragment │
│  └── ProfileFragment            └── OnboardingNotifications  │
│                                                               │
│  Adapters: TaskAdapter, WaterAdapter, MealAdapter, etc.     │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    VIEWMODEL/LOGIC LAYER                    │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ViewModel Classes:                                          │
│  ├── TaskViewModel                                           │
│  ├── WaterViewModel                                          │
│  ├── MealViewModel                                           │
│  ├── HabitViewModel                                          │
│  └── ProfileViewModel                                        │
│                                                               │
│  ViewModel Factories (Dependency Injection)                  │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                     REPOSITORY LAYER                        │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  Repositories:                                               │
│  ├── TaskRepository                                          │
│  ├── WaterRepository                                         │
│  ├── MealRepository                                          │
│  └── HabitRepository                                         │
│                                                               │
│  Business Logic & Data Orchestration                         │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                       DATA ACCESS LAYER                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  DAOs (Data Access Objects):                                 │
│  ├── TaskDao / TaskStreakDao                                │
│  ├── WaterLogDao / WaterStreakDao                            │
│  ├── MealDao / MealStreakDao                                 │
│  └── HabitDao / HabitLogDao / HabitStreakDao                │
│                                                               │
│  Type Converters:                                            │
│  └── LocalDateConverter (LocalDate ↔ String)                │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                  LOCAL STORAGE & PERSISTENCE                │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  Room Database (SQLite):                                     │
│  ├── tasks (TaskEntity)                                      │
│  ├── task_streaks (TaskStreakEntity)                         │
│  ├── meals (MealEntity)                                      │
│  ├── meal_streaks (MealStreakEntity)                         │
│  ├── water_logs (WaterLogEntity)                             │
│  ├── water_streaks (WaterStreakEntity)                       │
│  ├── habits (HabitEntity)                                    │
│  ├── habit_logs (HabitLogEntity)                             │
│  └── habit_streaks (HabitStreakEntity)                       │
│                                                               │
│  SharedPreferences:                                          │
│  └── progressly_prefs (User settings & preferences)          │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

---

## Data Flow Diagram

```
┌──────────┐
│  Fragment│  (User interacts)
└────┬─────┘
     │
     ↓
┌──────────────────────┐
│   ViewModel          │  (Handle business logic)
│  observes LiveData   │
└────┬─────────────────┘
     │
     ↓
┌──────────────────────┐
│   Repository         │  (Abstraction layer)
│  provides Flow       │
└────┬─────────────────┘
     │
     ↓
┌──────────────────────┐
│   DAO                │  (Database queries)
│  query execution     │
└────┬─────────────────┘
     │
     ↓
┌──────────────────────┐
│ Room Database        │  (Local SQLite)
│ persistent storage   │
└──────────────────────┘

            ↑ (Updates come back)
            │
    LiveData/Flow transform
            │
        ViewModel
            │
        Fragment/UI
```

---

## Feature Architecture

### Task Tracking Flow
```
User Input
    ↓
TasksFragment
    ↓
TaskViewModel.addTask()
    ↓
TaskRepository.insertTask()
    ↓
TaskDao.insertTask()
    ↓
Room Database
    ↓ (returns Flow)
LiveData
    ↓
Fragment displays list
```

### Water Tracking Flow
```
User clicks "250ml" button
    ↓
WaterFragment
    ↓
WaterViewModel.logWater(250)
    ↓
WaterRepository.insertWaterLog()
    ↓
WaterLogDao.insertWaterLog()
    ↓
Room Database + StreakUpdate
    ↓
LiveData emits updates
    ↓
UI refreshes: amount, progress, streak
```

### Habit Completion Flow
```
User taps checkmark
    ↓
HabitsFragment
    ↓
HabitViewModel.completeHabit(habitId)
    ↓
HabitRepository.logHabitCompletion()
    ↓
HabitLogDao.insertHabitLog()
    ↓
Room Database
    ↓ (recalculate streak)
HabitStreakDao.updateStreak()
    ↓
LiveData
    ↓
UI updated with new streak
```

---

## Package Structure

```
com.progressly.app/
│
├── ProgresslyApplication.kt (App entry point)
│
├── ui/
│   ├── MainActivity.kt (Main activity, nav setup)
│   │
│   ├── onboarding/
│   │   ├── OnboardingActivity.kt
│   │   └── fragments/
│   │       ├── OnboardingNameFragment.kt
│   │       ├── OnboardingAgeFragment.kt
│   │       ├── OnboardingGenderFragment.kt
│   │       ├── OnboardingWeightFragment.kt
│   │       └── OnboardingNotificationsFragment.kt
│   │
│   ├── fragments/
│   │   ├── TasksFragment.kt
│   │   ├── WaterFragment.kt
│   │   ├── MealsFragment.kt
│   │   ├── HabitsFragment.kt
│   │   └── ProfileFragment.kt
│   │
│   ├── adapters/
│   │   └── Adapters.kt (TaskAdapter, WaterAdapter, etc.)
│   │
│   └── viewmodels/
│       ├── TaskViewModel.kt
│       ├── WaterViewModel.kt
│       ├── MealViewModel.kt
│       ├── HabitViewModel.kt
│       └── ProfileViewModel.kt
│
├── data/
│   ├── database/
│   │   ├── ProgresslyDatabase.kt (Room database)
│   │   │
│   │   ├── dao/
│   │   │   └── Daos.kt (All DAOs)
│   │   │
│   │   └── entities/
│   │       └── Entities.kt (All entities)
│   │
│   ├── models/
│   │   └── Models.kt (Data classes)
│   │
│   ├── preferences/
│   │   └── PreferencesManager.kt (SharedPreferences wrapper)
│   │
│   └── repositories/
│       └── Repositories.kt (All repositories)
│
└── utils/
    ├── LocalDateConverter.kt (Room type converter)
    └── NotificationManager.kt (Local notifications)
```

---

## Class Dependency Graph

```
MainActivity
├── Repositories
│   ├── TaskRepository
│   ├── WaterRepository
│   ├── MealRepository
│   └── HabitRepository
└── PreferencesManager

TasksFragment
├── TaskViewModel
│   └── TaskRepository
└── TaskAdapter

WaterFragment
├── WaterViewModel
│   └── WaterRepository
└── WaterLogAdapter

ProfileFragment
├── ProfileViewModel
│   ├── TaskRepository
│   ├── WaterRepository
│   ├── MealRepository
│   └── HabitRepository
└── PreferencesManager

ProgresslyDatabase
├── Entities
│   ├── TaskEntity
│   ├── WaterLogEntity
│   ├── MealEntity
│   └── HabitEntity
└── DAOs
    ├── TaskDao
    ├── WaterLogDao
    ├── MealDao
    └── HabitDao
```

---

## Database Relationship Diagram

```
┌─────────────────────────────┐
│        TASKS                │
├─────────────────────────────┤
│ id: Int (PK)                │
│ title: String               │
│ description: String         │
│ isCompleted: Boolean        │
│ completedDate: LocalDate    │
│ createdDate: LocalDate      │
│ category: String            │
└─────────────────────────────┘
              ↕ (1:1)
┌─────────────────────────────┐
│    TASK_STREAKS             │
├─────────────────────────────┤
│ date: LocalDate (PK)        │
│ currentStreak: Int          │
│ longestStreak: Int          │
│ weeklyStreak: Int           │
└─────────────────────────────┘

┌─────────────────────────────┐
│        HABITS               │
├─────────────────────────────┤
│ id: Int (PK)                │
│ name: String                │
│ description: String         │
│ frequency: String           │
│ createdDate: LocalDate      │
│ category: String            │
└─────────────────────────────┘
              ↕ (1:N)
┌─────────────────────────────┐
│    HABIT_LOGS               │
├─────────────────────────────┤
│ id: Int (PK)                │
│ habitId: Int (FK)           │
│ completedDate: LocalDate    │
└─────────────────────────────┘
              ↕ (1:1)
┌─────────────────────────────┐
│    HABIT_STREAKS            │
├─────────────────────────────┤
│ habitId: Int (PK/FK)        │
│ currentStreak: Int          │
│ longestStreak: Int          │
│ lastCompletedDate: LocalDate│
└─────────────────────────────┘

┌─────────────────────────────┐
│      WATER_LOGS             │
├─────────────────────────────┤
│ id: Int (PK)                │
│ amount: Int (ml)            │
│ loggedDate: LocalDate       │
│ time: String                │
└─────────────────────────────┘
              ↕ (1:1)
┌─────────────────────────────┐
│    WATER_STREAKS            │
├─────────────────────────────┤
│ date: LocalDate (PK)        │
│ currentStreak: Int          │
│ longestStreak: Int          │
│ weeklyStreak: Int           │
│ dailyGoal: Int (ml)         │
└─────────────────────────────┘

┌─────────────────────────────┐
│        MEALS                │
├─────────────────────────────┤
│ id: Int (PK)                │
│ name: String                │
│ calories: Int               │
│ mealType: String            │
│ loggedDate: LocalDate       │
│ time: String                │
└─────────────────────────────┘
              ↕ (1:1)
┌─────────────────────────────┐
│    MEAL_STREAKS             │
├─────────────────────────────┤
│ date: LocalDate (PK)        │
│ currentStreak: Int          │
│ longestStreak: Int          │
│ weeklyStreak: Int           │
└─────────────────────────────┘
```

---

## State Management Flow

```
User Action (Click, Input)
         ↓
Fragment handles event
         ↓
ViewModel.function()
         ↓
LiveData.postValue() or Flow.emit()
         ↓
Fragment observes changes
         ↓
Adapter.submitList() or UI.setText()
         ↓
Screen updated
```

---

## Lifecycle Integration

```
Fragment Lifecycle
├── onCreateView()
│   └── Inflate layout
├── onViewCreated()
│   ├── Initialize views
│   ├── Create ViewModel
│   └── Observe LiveData
├── onResume()
│   └── Fragment visible
└── onDestroyView()
    └── Clean up observers

ViewModel Lifecycle
├── Created (when Fragment created)
├── Live (while Fragment exists)
└── Destroyed (when Fragment destroyed)

LiveData Lifecycle
├── Inactive (no observers)
├── Active (has active observers)
└── Emits only to active observers
```

---

## Coroutine Scope Usage

```
viewModelScope.launch {
    // Code runs in IO dispatcher by default
    repository.insertTask(task)  // Suspended, doesn't block UI
    
    // Once done, automatically posts value to main thread
    taskLiveData.postValue(result)
}

// Fragment observes on main thread
viewModel.getTasks().observe(viewLifecycleOwner) { tasks ->
    // UI update happens safely on main thread
    adapter.submitList(tasks)
}
```

---

## Thread Safety

```
Main Thread (UI Thread)
├── Fragment/Activity code
├── ViewModel code
├── Adapter binding
└── LiveData observation

Background Threads (IO Thread)
├── Database queries
├── File I/O
└── Heavy computations

Coroutines Handle Switching
├── viewModelScope.launch { } → IO
├── repository.insert() → Suspending
└── postValue() → Back to Main

Result: Safe, non-blocking updates
```

---

## Layout File Structure

```
res/layout/
│
├── activity_main.xml
│   └── Bottom navigation + Fragment container
│
├── activity_onboarding.xml
│   └── Fragment container for setup
│
├── fragment_*.xml (5 main fragments)
│   ├── fragment_tasks.xml
│   ├── fragment_water.xml
│   ├── fragment_meals.xml
│   ├── fragment_habits.xml
│   └── fragment_profile.xml
│
├── fragment_onboarding_*.xml (5 onboarding fragments)
│   ├── fragment_onboarding_name.xml
│   ├── fragment_onboarding_age.xml
│   ├── fragment_onboarding_gender.xml
│   ├── fragment_onboarding_weight.xml
│   └── fragment_onboarding_notifications.xml
│
└── item_*.xml (RecyclerView item layouts)
    ├── item_task.xml
    ├── item_water_log.xml
    ├── item_meal.xml
    └── item_habit.xml
```

---

## Material Design 3 Implementation

```
Theme System
├── colors.xml (Light mode)
│   ├── primary: #6750A4
│   ├── secondary: #625B71
│   ├── tertiary: #7D5260
│   ├── success: #1E7E34
│   ├── warning: #F9A825
│   └── error: #B3261E
│
├── values-night/colors.xml (Dark mode)
│   └── All colors inverted for dark
│
└── values-night/styles.xml
    └── Theme.Progressly.Dark

Components Used
├── MaterialButton
├── FloatingActionButton
├── TextInputLayout + TextInputEditText
├── Card + CardView
├── RecyclerView
├── ProgressBar
├── BottomNavigationView
└── Material Icons
```

---

## Performance Optimization Points

```
Database
├── Indexed columns on frequently queried fields
├── Type-safe queries with DAOs
└── Room handles threading automatically

UI
├── ViewBinding (compile-time safety)
├── RecyclerView with ListAdapter (efficient diffing)
├── View reuse and recycling
└── Coroutine suspension (non-blocking)

Memory
├── Fragment retention policies
├── ViewModel survives config changes
├── LiveData scoped to lifecycle
└── Proper coroutine cancellation

Startup
├── Lazy initialization of repositories
├── Direct dependency injection (no reflection)
├── Minimal reflection from Room
└── Native compiled patterns
```

---

## Testing Architecture

```
Unit Tests
├── ViewModel tests
├── Repository tests
├── Utility function tests
└── Database query tests

Instrumented Tests
├── Fragment tests
├── Adapter tests
├── Database integration tests
└── UI interaction tests

Test Structure
├── src/test/ (Unit tests)
└── src/androidTest/ (Instrumented tests)
```

---

## Build Configuration

```
build.gradle.kts (Root)
└── Versions and plugins

app/build.gradle.kts
├── Android configuration
├── Compilation settings
├── Signing configuration
└── Dependencies

ProGuard
├── Keeps app classes
├── Preserves AndroidX APIs
├── Maintains Room entities
└── Obfuscates other code
```

---

## File Size Breakdown (Estimated)

```
APK Components
├── Code (classes.dex)        ~3.5 MB
├── Resources (resources.pb)  ~2.0 MB
├── Libraries                 ~4.5 MB
├── Manifest & metadata       ~0.5 MB
├── Assets                    ~0.5 MB
└── Total Debug APK           ~11 MB (with symbols)
└── Total Release APK         ~5-7 MB (after proguard)
```

---

This architecture ensures:
- ✅ Separation of concerns
- ✅ Testability
- ✅ Maintainability
- ✅ Scalability
- ✅ Type safety
- ✅ Thread safety
- ✅ Performance
- ✅ Clean code practices
