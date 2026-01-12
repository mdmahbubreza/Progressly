# 📦 Dependencies & Libraries - Progressly

Complete list of all libraries and dependencies used in the Progressly app.

## Gradle Configuration

### Build Tools & SDK
- **Gradle Version**: 8.2.0
- **Kotlin Version**: 1.9.21
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Java Version**: 17

---

## Android Framework Libraries

### AndroidX Core
```gradle
androidx:core-ktx:1.12.0
```
- Kotlin extensions for Android core components
- Provides utility functions for common Android tasks

### AppCompat
```gradle
androidx:appcompat:1.6.1
```
- Backward compatibility for newer Android features
- Ensures app runs on older Android versions

### Activity & Fragment
```gradle
androidx:activity:activity-ktx:1.8.0
androidx:fragment:fragment-ktx:1.6.1
```
- Kotlin extensions for Activity and Fragment
- Lifecycle-aware components
- Fragment result APIs

---

## Material Design & UI

### Material Design 3
```gradle
com.google.android.material:material:1.11.0
```
- Latest Material Design components
- Color system and theming
- Buttons, cards, navigation components
- Material 3 styles and themes

---

## Navigation

### AndroidX Navigation
```gradle
androidx:navigation:navigation-fragment-ktx:2.7.4
androidx:navigation:navigation-ui-ktx:2.7.4
```
- Fragment-based navigation
- Navigation graph XML
- Safe Args for type-safe navigation
- Bottom navigation integration

---

## Database

### Room Database
```gradle
androidx:room:room-runtime:2.6.1
androidx:room:room-ktx:2.6.1
kapt androidx:room:room-compiler:2.6.1
```
- ORM for SQLite database
- Type-safe database access
- Coroutine support
- LiveData/Flow integration
- Annotation processors for code generation

### Features Used
- `@Database` - Database definition
- `@Entity` - Database tables
- `@Dao` - Data access objects
- `@Query` - Database queries
- `@Insert`, `@Update`, `@Delete` - CRUD operations
- Type converters for custom types

---

## Lifecycle & Concurrency

### Lifecycle
```gradle
androidx:lifecycle:lifecycle-runtime-ktx:2.6.2
androidx:lifecycle:lifecycle-viewmodel-ktx:2.6.2
```
- Lifecycle-aware components
- ViewModel for MVVM architecture
- LiveData for reactive updates
- Lifecycle observers

### Coroutines
```gradle
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3
```
- Asynchronous programming
- Non-blocking operations
- ViewModelScope for lifecycle-aware coroutines
- Flow for reactive streams
- Launch and async builders

---

## Background Work

### Work Manager
```gradle
androidx:work:work-runtime-ktx:2.8.1
```
- Scheduling background tasks
- Reliable job scheduling
- Workmanager for notifications
- Alarm scheduling

---

## Data Persistence

### DataStore
```gradle
androidx:datastore:datastore-preferences:1.0.0
```
- Modern replacement for SharedPreferences
- Type-safe settings storage
- Coroutine support
- Encryption support

**Used for**: User preferences, settings, configuration

---

## Charts & Visualization

### MPAndroidChart
```gradle
com.github.PhilJay:MPAndroidChart:v3.1.0
```
- Advanced charting library
- Multiple chart types
- Animations and interactions
- Touch gestures

**Used for**: Weekly progress charts, statistics visualization

---

## Icons & Graphics

### Material Icons Extended
```gradle
androidx.compose.material:material-icons-extended:1.6.0
```
- Comprehensive icon set
- Material Design 3 icons
- Vector drawable support
- Easy icon implementation

---

## Animations

### Lottie
```gradle
com.airbnb.android:lottie:6.1.0
```
- Lightweight animation library
- JSON animation support
- Smooth transitions
- Performance optimized

---

## Testing

### Unit Tests
```gradle
junit:junit:4.13.2
```
- JUnit 4 framework
- Unit test execution
- Assertions and matchers

### Instrumented Tests
```gradle
androidx:test:ext:junit:1.1.5
androidx:test:espresso:espresso-core:3.5.1
```
- Android instrumentation tests
- Espresso for UI testing
- Test runner for on-device tests

---

## Plugin Configuration

### Kotlin Android Plugin
```gradle
id("org.jetbrains.kotlin.android") version "1.9.21"
```
- Kotlin compilation for Android
- Language features support
- Interoperability with Java

### Kotlin Kapt
```gradle
id("kotlin-kapt")
```
- Kotlin Annotation Processing Tool
- Generates code from annotations
- Used by Room for database code generation

---

## ProGuard Configuration

Rules configured for:
- **App classes**: Full keep
- **AndroidX**: Preservation of public APIs
- **Room**: Database and entity preservation
- **Coroutines**: MainDispatcher preservation

---

## Version Management

All library versions are managed in `build.gradle.kts` for easy updates:

```kotlin
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")
// etc...
```

---

## Dependency Tree

```
Progressly App
├── Android Framework
│   ├── Core (AndroidX)
│   ├── AppCompat
│   ├── Activity & Fragment
│   └── Navigation
├── UI Layer
│   ├── Material Design 3
│   ├── MPAndroidChart
│   ├── Lottie
│   └── Material Icons
├── Data Layer
│   ├── Room Database
│   ├── DataStore
│   └── Type Converters
├── Concurrency
│   ├── Coroutines
│   ├── Lifecycle
│   └── LiveData
├── Background Work
│   └── WorkManager
└── Testing
    ├── JUnit 4
    └── Espresso
```

---

## Minimum Versions Required

| Component | Version | Reason |
|-----------|---------|--------|
| Kotlin | 1.9.21 | Latest features, optimization |
| Android SDK | 26 (Android 8.0) | API availability |
| Gradle | 8.2.0 | Modern build system |
| Java | 17 | Latest JVM features |

---

## Optional Dependencies

The following can be added in future versions:

```gradle
// Firebase (optional - for future cloud sync)
com.google.firebase:firebase-firestore-ktx

// Network (optional - for future backend)
com.squareup.retrofit2:retrofit

// Image Loading (optional - for meal photos)
com.github.bumptech.glide:glide

// Encryption (optional - for security)
androidx.security:security-crypto

// Preferences encryption (optional)
androidx.datastore:datastore-encrypted

// PDF Export (optional - for data export)
com.itextpdf:itextpdf
```

---

## Deprecated Libraries NOT Used

The following are **intentionally NOT included**:

- ❌ **ViewModelFactory with DI frameworks** - Manual factories for simplicity
- ❌ **Dagger/Hilt** - Too complex for this app, manual DI sufficient
- ❌ **Network libraries** - App is offline-first
- ❌ **RxJava** - Coroutines are preferred
- ❌ **Moshi/Gson** - No JSON serialization needed
- ❌ **Retrofit** - No API calls
- ❌ **Firebase** - Data stays local
- ❌ **Compose** - XML layouts chosen for simplicity

---

## License Compliance

All used libraries are open-source and compatible with MIT:

- AndroidX: Apache 2.0
- Material Design 3: Apache 2.0
- Room: Apache 2.0
- Coroutines: Apache 2.0
- MPAndroidChart: Apache 2.0
- Lottie: Apache 2.0
- JUnit: EPL 1.0
- Espresso: Apache 2.0

---

## Performance Impact

| Library | Size | Impact | Notes |
|---------|------|--------|-------|
| Room | ~1.2MB | Medium | Critical for database |
| Material | ~3.5MB | High | UI framework |
| Coroutines | ~0.8MB | Low | Efficient concurrency |
| Lifecycle | ~0.5MB | Low | Memory management |
| Navigation | ~0.6MB | Low | Fragment handling |
| Charts | ~1.0MB | Medium | Optional visualization |
| Lottie | ~0.3MB | Low | Optional animations |

**Total APK Impact**: ~200-300KB (debug), ~100-150KB (release with proguard)

---

## Update Strategy

### Critical Updates
- Security patches for Android components
- Room database stability fixes
- Coroutines critical updates

### Regular Updates
- Quarterly update cycle
- New Material Design features
- Performance improvements

### Breaking Changes
- Major version updates tested thoroughly
- Database schema version incremented
- Migration paths provided

---

## Troubleshooting Dependencies

### Gradle Sync Issues
```bash
./gradlew clean
./gradlew sync
```

### Version Conflicts
- Check `gradle.properties` for JVM args
- Clear Gradle cache: `~/.gradle/`
- Rebuild project

### Dependency Errors
- Check minimum SDK compatibility
- Verify Java version matches (17+)
- Check ProGuard rules aren't removing needed classes

---

## Future Considerations

### Potential Additions
- **Jetpack Compose**: For modern UI (v2.0)
- **Hilt**: For dependency injection (v2.0)
- **Room async features**: In-built async (v3.0)
- **Kotlin Multiplatform**: For desktop/web (v3.0+)

### Maintenance
- Monthly dependency checks
- Quarterly security audits
- Annual architecture review

---

## Support

For dependency issues or questions:
1. Check Android documentation
2. Review library changelogs
3. Check GitHub issues
4. Consult Stack Overflow
5. Open issue on project GitHub

---

**Last Updated**: January 12, 2025  
**Progressly Version**: 1.0.0
