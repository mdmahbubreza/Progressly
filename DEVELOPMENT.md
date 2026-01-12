# 🛠️ Development Guide - Progressly

A comprehensive guide for developing, building, and extending the Progressly Android app.

## Table of Contents
1. [Environment Setup](#environment-setup)
2. [Building & Running](#building--running)
3. [Architecture Details](#architecture-details)
4. [Adding New Features](#adding-new-features)
5. [Testing](#testing)
6. [Troubleshooting](#troubleshooting)

---

## Environment Setup

### System Requirements
- **OS**: Windows, macOS, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Storage**: 10GB free space
- **JDK**: Java 17 or higher

### Install Android Studio
1. Download from [developer.android.com](https://developer.android.com/studio)
2. Run installer and follow setup wizard
3. Install Android SDK:
   - API Level 26 (Android 8.0) - Minimum
   - API Level 34 (Android 14) - Target
4. Install Kotlin plugin (usually pre-installed)

### Project Setup
```bash
# Clone repository
git clone https://github.com/mdmahbubreza/Progressly.git
cd Progressly

# Sync Gradle
./gradlew sync

# Build release
./gradlew assembleRelease

# Build debug
./gradlew assembleDebug

# Run tests
./gradlew test
```

---

## Building & Running

### Building Variants

#### Debug Build
```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

#### Release Build
```bash
./gradlew assembleRelease
# APK: app/build/outputs/apk/release/app-release.apk
```

### Running on Emulator
1. Create AVD in Android Studio (Virtual Device Manager)
2. Select Android 26+ device
3. Run app:
   ```bash
   ./gradlew installDebug
   adb shell am start -n com.progressly.app/.ui.MainActivity
   ```

### Running on Physical Device
1. Enable Developer Mode:
   - Settings → About → Tap Build Number 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging
3. Connect device via USB
4. In Android Studio: Run → Select Device

### APK Installation
```bash
# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Uninstall app
adb uninstall com.progressly.app

# View logs
adb logcat -s "Progressly"

# Clear app data
adb shell pm clear com.progressly.app
```

---

## Architecture Details

### MVVM Components

#### ViewModel Examples
```kotlin
// TaskViewModel.kt
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    fun getTasksByDate(date: LocalDate) = 
        repository.getTasksByDate(date).asLiveData()

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.insertTask(TaskEntity(title = title))
        }
    }
}
```

#### Repository Pattern
```kotlin
// TaskRepository.kt
class TaskRepository(
    private val taskDao: TaskDao,
    private val taskStreakDao: TaskStreakDao
) {
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDate(date)
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }
}
```

### Database Schema

#### Room Setup
```kotlin
@Database(
    entities = [...],
    version = 1,
    exportSchema = false
)
abstract class ProgresslyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    // ... other DAOs
}
```

### Data Flow
```
UI (Fragment)
    ↓
ViewModel (Emits LiveData<List<T>>)
    ↓
Repository (Provides Flow<List<T>>)
    ↓
DAO (Database queries)
    ↓
Room Database (SQLite)
```

---

## Adding New Features

### Example: Adding a New Tracking Category

#### 1. Create Entity
```kotlin
// entities/Entities.kt
@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val duration: Int, // minutes
    val intensity: String, // Light, Moderate, High
    val loggedDate: LocalDate = LocalDate.now()
)
```

#### 2. Create DAO
```kotlin
// dao/Daos.kt
@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE loggedDate = :date")
    fun getExercisesByDate(date: LocalDate): Flow<List<ExerciseEntity>>
}
```

#### 3. Add to Database
```kotlin
// ProgresslyDatabase.kt
@Database(
    entities = [
        // ... existing entities
        ExerciseEntity::class
    ],
    version = 2 // Increment version
)
abstract class ProgresslyDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}
```

#### 4. Create Repository
```kotlin
// repositories/Repositories.kt
class ExerciseRepository(
    private val exerciseDao: ExerciseDao
) {
    fun getExercisesByDate(date: LocalDate) = 
        exerciseDao.getExercisesByDate(date)

    suspend fun insertExercise(exercise: ExerciseEntity) {
        exerciseDao.insertExercise(exercise)
    }
}
```

#### 5. Create ViewModel
```kotlin
// viewmodels/ExerciseViewModel.kt
class ExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {
    fun getExercisesByDate(date: LocalDate) = 
        repository.getExercisesByDate(date).asLiveData()

    fun logExercise(name: String, duration: Int, intensity: String) {
        viewModelScope.launch {
            val exercise = ExerciseEntity(
                name = name,
                duration = duration,
                intensity = intensity
            )
            repository.insertExercise(exercise)
        }
    }
}
```

#### 6. Create Fragment & Adapter
```kotlin
// fragments/ExerciseFragment.kt
class ExerciseFragment : Fragment() {
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.getExercisesByDate(LocalDate.now())
            .observe(viewLifecycleOwner) { exercises ->
                adapter.submitList(exercises)
            }
    }
}
```

#### 7. Add Navigation
Update `bottom_nav_menu.xml` and `MainActivity.kt` to include the new section.

---

## Testing

### Unit Tests
```kotlin
// Test ViewModel
@Test
fun testAddTask() {
    val repository = mockk<TaskRepository>()
    val viewModel = TaskViewModel(repository)
    
    viewModel.addTask("Test Task")
    
    verify { repository.insertTask(any()) }
}
```

### Running Tests
```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests com.progressly.app.ExampleTest

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Test Structure
```
app/src/
├── test/
│   └── java/com/progressly/app/
│       ├── viewmodels/
│       ├── repositories/
│       └── utils/
└── androidTest/
    └── java/com/progressly/app/
        ├── ui/
        └── database/
```

---

## Troubleshooting

### Build Issues

#### Gradle Sync Fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew sync
./gradlew build
```

#### Duplicate Class Error
- Clear build cache: `./gradlew clean`
- Invalidate Android Studio cache: File → Invalidate Caches

### Runtime Issues

#### App Crashes on Startup
- Check logcat: `adb logcat -s "Progressly"`
- Verify minimum SDK: Ensure device is API 26+
- Clear app data: `adb shell pm clear com.progressly.app`

#### Database Migration Issues
```kotlin
// In ProgresslyDatabase
fallbackToDestructiveMigration = true // Dangerous - only for dev
```

#### LiveData Not Updating
- Ensure Fragment is attached to Activity
- Check observer is added in onViewCreated (not onCreate)
- Verify Fragment not detached prematurely

### Performance Issues

#### Slow Database Queries
- Add indexes to frequently queried columns
- Use pagination for large datasets
- Profile with Android Profiler

#### Memory Leaks
- Use `viewLifecycleOwner` for observers
- Cancel coroutines in onDestroy
- Remove listeners in onDestroyView

---

## Code Style Guide

### Naming Conventions
```kotlin
// Classes
class TaskViewModel { }
class TaskRepository { }

// Functions
fun addTask() { }
fun getTasksByDate() { }

// Variables
val taskList: List<TaskEntity>
var selectedTask: TaskEntity

// Resources
@string/task_title
@color/primary
@drawable/ic_task
@layout/fragment_tasks
```

### Best Practices
1. Use descriptive names
2. Keep functions small (< 20 lines)
3. Follow Kotlin conventions
4. Add documentation for complex logic
5. Use sealed classes for state
6. Prefer immutable data classes

---

## Build Configuration

### gradle.properties
```properties
org.gradle.jvmargs=-Xmx4096m
android.useAndroidX=true
kotlin.code.style=official
```

### Signing Release Builds
```bash
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=path/to/keystore \
  -Pandroid.injected.signing.store.password=xxx \
  -Pandroid.injected.signing.key.alias=xxx \
  -Pandroid.injected.signing.key.password=xxx
```

---

## Useful Commands

```bash
# Clear local cache
./gradlew clean

# Build APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Run tests
./gradlew test

# Generate docs
./gradlew dokka

# Format code
./gradlew spotlessApply

# Run linter
./gradlew lint
```

---

## Resources

- [Android Documentation](https://developer.android.com/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [MVVM Architecture](https://developer.android.com/jetpack/guide)
- [Material Design 3](https://m3.material.io/)

---

**Happy Coding! 🚀**
