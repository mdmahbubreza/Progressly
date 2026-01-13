import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class ProgresslyDatabase {
  static final ProgresslyDatabase _instance = ProgresslyDatabase._internal();
  static Database? _database;

  factory ProgresslyDatabase() {
    return _instance;
  }

  ProgresslyDatabase._internal();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initializeDatabase();
    return _database!;
  }

  Future<Database> _initializeDatabase() async {
    final databasePath = await getDatabasesPath();
    final path = join(databasePath, 'progressly.db');

    return openDatabase(
      path,
      version: 1,
      onCreate: _createDatabase,
    );
  }

  Future<void> _createDatabase(Database db, int version) async {
    // Tasks table
    await db.execute('''
      CREATE TABLE tasks (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT NOT NULL,
        description TEXT DEFAULT '',
        isCompleted INTEGER DEFAULT 0,
        completedDate TEXT,
        createdDate TEXT NOT NULL,
        category TEXT DEFAULT 'General'
      )
    ''');

    // Task Streaks table
    await db.execute('''
      CREATE TABLE task_streaks (
        date TEXT PRIMARY KEY,
        currentStreak INTEGER DEFAULT 0,
        longestStreak INTEGER DEFAULT 0,
        weeklyStreak INTEGER DEFAULT 0
      )
    ''');

    // Meals table
    await db.execute('''
      CREATE TABLE meals (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        calories INTEGER DEFAULT 0,
        mealType TEXT DEFAULT 'Breakfast',
        loggedDate TEXT NOT NULL,
        time TEXT DEFAULT ''
      )
    ''');

    // Meal Streaks table
    await db.execute('''
      CREATE TABLE meal_streaks (
        date TEXT PRIMARY KEY,
        currentStreak INTEGER DEFAULT 0,
        longestStreak INTEGER DEFAULT 0,
        weeklyStreak INTEGER DEFAULT 0
      )
    ''');

    // Water Logs table
    await db.execute('''
      CREATE TABLE water_logs (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        amount INTEGER NOT NULL,
        loggedDate TEXT NOT NULL,
        time TEXT DEFAULT ''
      )
    ''');

    // Water Streaks table
    await db.execute('''
      CREATE TABLE water_streaks (
        date TEXT PRIMARY KEY,
        currentStreak INTEGER DEFAULT 0,
        longestStreak INTEGER DEFAULT 0,
        weeklyStreak INTEGER DEFAULT 0,
        dailyGoal INTEGER DEFAULT 2000
      )
    ''');

    // Habits table
    await db.execute('''
      CREATE TABLE habits (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        description TEXT DEFAULT '',
        frequency TEXT DEFAULT 'Daily',
        createdDate TEXT NOT NULL,
        category TEXT DEFAULT 'General'
      )
    ''');

    // Habit Logs table
    await db.execute('''
      CREATE TABLE habit_logs (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        habitId INTEGER NOT NULL,
        completedDate TEXT NOT NULL,
        FOREIGN KEY(habitId) REFERENCES habits(id) ON DELETE CASCADE
      )
    ''');

    // Habit Streaks table
    await db.execute('''
      CREATE TABLE habit_streaks (
        habitId INTEGER PRIMARY KEY,
        currentStreak INTEGER DEFAULT 0,
        longestStreak INTEGER DEFAULT 0,
        weeklyStreak INTEGER DEFAULT 0,
        lastCompletedDate TEXT,
        FOREIGN KEY(habitId) REFERENCES habits(id) ON DELETE CASCADE
      )
    ''');
  }

  Future<void> close() async {
    if (_database != null) {
      await _database!.close();
      _database = null;
    }
  }
}
