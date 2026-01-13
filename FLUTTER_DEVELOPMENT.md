# 🔧 Flutter Development Guide - Progressly

## Project Overview

This is a complete Kotlin → Flutter migration of the Progressly wellness tracking app. The project uses:
- **Architecture**: MVVM + Repository + DAO patterns
- **State Management**: GetX
- **Database**: SQLite (sqflite)
- **UI**: Material Design 3 with dark mode

---

## 🏗️ Project Structure Explained

### 1. **lib/main.dart**
Entry point of the application. Handles:
- App initialization
- Theme configuration
- Navigation setup
- Onboarding check

### 2. **lib/config/theme/app_theme.dart**
Centralized theming with:
- Color definitions
- Typography
- Component styles
- Light and dark themes

### 3. **lib/data/ - Data Layer**

#### models/models.dart
All data classes:
- `UserProfile` - User information
- `TaskModel`, `MealModel`, `WaterLogModel`, `HabitModel`
- `StreakData`, `DailySummary`

Each model has:
- Constructor with defaults
- `toJson()` for serialization
- `fromJson()` for deserialization
- `copyWith()` for immutable updates

#### database/progressly_database.dart
SQLite database configuration:
- Database initialization
- Schema creation (9 tables)
- Singleton pattern

#### database/dao.dart
Data Access Objects for each entity:
- `TaskDao` - CRUD operations for tasks
- `MealDao` - CRUD operations for meals
- `WaterLogDao` - CRUD operations for water logs
- `HabitDao` - CRUD operations for habits

Each DAO handles:
- Insert operations
- Select (all, by ID, by date)
- Update operations
- Delete operations
- Aggregation queries (sum, count)

#### repositories/repositories.dart
Business logic layer:
- `TaskRepository`
- `MealRepository`
- `WaterRepository`
- `HabitRepository`

Repositories act as:
- Facade for DAOs
- Business logic abstraction
- Data source change flexibility

### 4. **lib/services/**

#### local_storage_service.dart
SharedPreferences wrapper for:
- User profile storage
- Onboarding completion
- Notification preferences
- Daily water goal
- Helper methods for calculations

#### notification_service.dart
Flutter Local Notifications wrapper:
- Notification initialization
- Scheduled notifications
- Convenience reminder methods
- Permission handling

### 5. **lib/features/ - Feature Modules**

#### onboarding/presentation/pages/
User onboarding flow:
- `OnboardingPage` - Page controller setup
- `NameScreen` - User name input
- `AgeScreen` - Age selection
- `GenderScreen` - Gender selection
- `WeightScreen` - Weight input
- `NotificationsScreen` - Permission request
- `OnboardingController` - State management

#### home/presentation/pages/
Main app screens:
- `HomePage` - Bottom navigation setup
- `TasksScreen` - Task management
- `WaterScreen` - Water tracking
- `MealsScreen` - Meal logging
- `HabitsScreen` - Habit tracking
- `ProfileScreen` - User profile & stats

#### viewmodels/view_models.dart
State management using GetX:
- `TaskViewModel` - Task logic
- `MealViewModel` - Meal logic
- `WaterViewModel` - Water tracking logic
- `HabitViewModel` - Habit logic
- `ProfileViewModel` - Profile & stats logic

---

## 📊 Data Flow Architecture

```
User Interaction (UI)
         ↓
    Screen Widget
         ↓
   GetX Controller (ViewModel)
         ↓
   Repository Layer
         ↓
   DAO Layer
         ↓
   SQLite Database
```

### Example: Adding a Task

1. User taps "+" button on TasksScreen
2. Dialog shows with task input fields
3. User enters title and taps "Add"
4. Screen calls `viewModel.addTask(task)`
5. ViewModel calls `repository.addTask(task)`
6. Repository calls `taskDao.insertTask(task)`
7. DAO executes SQL INSERT
8. Database returns new task ID
9. ViewModel adds task to observable list
10. UI rebuilds reactively

---

## 🔄 Adding a New Feature

### Step 1: Create Data Model
In `lib/data/models/models.dart`:
```dart
class NewFeatureModel {
  final int? id;
  final String name;
  
  NewFeatureModel({
    this.id,
    required this.name,
  });
  
  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
  };
  
  factory NewFeatureModel.fromJson(Map<String, dynamic> json) => 
    NewFeatureModel(
      id: json['id'],
      name: json['name'],
    );
}
```

### Step 2: Create DAO
In `lib/data/database/dao.dart`:
```dart
class NewFeatureDao {
  final _database = ProgresslyDatabase();
  
  Future<int> insertNewFeature(NewFeatureModel feature) async {
    final db = await _database.database;
    return db.insert('new_features', {
      'name': feature.name,
    });
  }
  
  // Add other CRUD methods...
}
```

### Step 3: Create Repository
In `lib/data/repositories/repositories.dart`:
```dart
class NewFeatureRepository {
  final newFeatureDao = NewFeatureDao();
  
  Future<int> addNewFeature(NewFeatureModel feature) => 
    newFeatureDao.insertNewFeature(feature);
    
  // Add other business logic methods...
}
```

### Step 4: Create ViewModel
In `lib/features/viewmodels/view_models.dart`:
```dart
class NewFeatureViewModel extends GetxController {
  final repository = NewFeatureRepository();
  
  final items = <NewFeatureModel>[].obs;
  final isLoading = false.obs;
  
  @override
  void onInit() {
    super.onInit();
    loadItems();
  }
  
  Future<void> loadItems() async {
    isLoading.value = true;
    try {
      final loaded = await repository.getItems();
      items.assignAll(loaded);
    } finally {
      isLoading.value = false;
    }
  }
}
```

### Step 5: Create UI Screen
In `lib/features/home/presentation/pages/new_feature_screen.dart`:
```dart
class NewFeatureScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<NewFeatureViewModel>();
    
    return Obx(() {
      if (viewModel.isLoading.value) {
        return Center(child: CircularProgressIndicator());
      }
      
      return ListView(
        children: viewModel.items.map((item) => 
          ListTile(title: Text(item.name))
        ).toList(),
      );
    });
  }
}
```

---

## 🧪 Testing Guidelines

### Unit Tests
```dart
// test/repositories/task_repository_test.dart
void main() {
  group('TaskRepository', () {
    late TaskRepository repository;
    
    setUp(() {
      repository = TaskRepository();
    });
    
    test('addTask returns int', () async {
      final task = TaskModel(title: 'Test Task');
      final id = await repository.addTask(task);
      expect(id, isA<int>());
    });
  });
}
```

### Widget Tests
```dart
// test/features/home/tasks_screen_test.dart
void main() {
  testWidgets('TasksScreen displays tasks', (tester) async {
    await tester.pumpWidget(MaterialApp(
      home: TasksScreen(),
    ));
    
    expect(find.byType(ListView), findsOneWidget);
  });
}
```

---

## 🚀 Performance Tips

### 1. Lazy Loading
```dart
Future<List<TaskModel>> getTasksByDate(DateTime date) async {
  // Load only tasks for specific date
  return taskDao.getTasksByDate(date);
}
```

### 2. Caching
```dart
final _cache = <int, TaskModel>{};

Future<TaskModel?> getTaskById(int id) async {
  if (_cache.containsKey(id)) {
    return _cache[id];
  }
  
  final task = await taskDao.getTaskById(id);
  if (task != null) {
    _cache[id] = task;
  }
  return task;
}
```

### 3. Pagination
```dart
Future<List<TaskModel>> getTasksPaginated(int page, int pageSize) async {
  final offset = (page - 1) * pageSize;
  return taskDao.getTasksWithLimit(pageSize, offset);
}
```

### 4. Use const constructors
```dart
// Good
const CircularProgressIndicator()

// Avoid
CircularProgressIndicator()
```

---

## 📝 Code Style Guidelines

### Naming Conventions
- **Files**: `snake_case` (e.g., `tasks_screen.dart`)
- **Classes**: `PascalCase` (e.g., `TaskModel`)
- **Variables**: `camelCase` (e.g., `taskTitle`)
- **Constants**: `camelCase` (e.g., `maxTasks`)

### Comments
```dart
// Single line comment
/// Documentation comment (for public APIs)

/// Add a task to the database.
///
/// Returns the ID of the newly created task.
Future<int> addTask(TaskModel task) async {
  // Implementation
}
```

### Widget Organization
```dart
class MyScreen extends StatelessWidget {
  const MyScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: _buildBody(),
      floatingActionButton: _buildFAB(),
    );
  }

  Widget _buildAppBar() { ... }
  Widget _buildBody() { ... }
  Widget _buildFAB() { ... }
}
```

---

## 🔐 Security Best Practices

1. **Never hardcode secrets**
   ```dart
   // Bad
   const String apiKey = 'secret_key_123';
   
   // Good
   String apiKey = dotenv.env['API_KEY'] ?? '';
   ```

2. **Validate user input**
   ```dart
   if (title.isEmpty) {
     Get.snackbar('Error', 'Title cannot be empty');
     return;
   }
   ```

3. **Use try-catch for database operations**
   ```dart
   try {
     await repository.addTask(task);
   } catch (e) {
     Get.snackbar('Error', 'Failed to add task: $e');
   }
   ```

---

## 🐛 Debugging Tips

### Hot Reload vs Hot Restart
```bash
# Hot Reload (preserves state)
Press 'r' in terminal

# Hot Restart (clears state)
Press 'R' in terminal
```

### Verbose Logging
```bash
flutter run -v
```

### Debug Database
```dart
// Get all tasks
final tasks = await taskDao.getAllTasks();
print('All tasks: ${tasks.length}');
print(tasks.map((t) => t.title).toList());
```

### DevTools
```bash
flutter pub global activate devtools
devtools
```

---

## 📦 Adding Dependencies

### 1. Add to pubspec.yaml
```yaml
dependencies:
  new_package: ^1.0.0
```

### 2. Get dependencies
```bash
flutter pub get
```

### 3. Import in your file
```dart
import 'package:new_package/new_package.dart';
```

---

## 🔄 Updating Dependencies

```bash
# Check outdated packages
flutter pub outdated

# Upgrade all packages
flutter pub upgrade

# Upgrade specific package
flutter pub upgrade package_name
```

---

## 📱 Platform-Specific Code

### Android Only
```dart
if (defaultTargetPlatform == TargetPlatform.android) {
  // Android specific code
}
```

### iOS Only
```dart
if (defaultTargetPlatform == TargetPlatform.iOS) {
  // iOS specific code
}
```

---

## 🚀 Building for Release

### Android APK
```bash
flutter build apk --release
# Output: build/app/outputs/flutter-apk/app-release.apk
```

### Android App Bundle
```bash
flutter build appbundle --release
# Output: build/app/outputs/bundle/release/app-release.aab
```

### iOS
```bash
flutter build ios --release
# Output: build/ios/iphoneos/Runner.app
```

---

## 📚 Resources

- [Flutter Documentation](https://flutter.dev/docs)
- [Dart Language Tour](https://dart.dev/guides/language/language-tour)
- [GetX Documentation](https://github.com/jonataslaw/getx)
- [sqflite Documentation](https://pub.dev/packages/sqflite)
- [Material Design 3](https://m3.material.io/)

---

## 💪 Best Practices

1. **Always use const constructors** - Better performance
2. **Avoid nested setState** - Use GetX instead
3. **Keep widgets pure** - No side effects in build
4. **Use named parameters** - Better code readability
5. **Write meaningful comments** - For complex logic
6. **Test regularly** - Use hot reload frequently
7. **Clean code** - Follow Dart style guide
8. **Handle errors gracefully** - Always show user feedback

---

Happy coding! 🎉
