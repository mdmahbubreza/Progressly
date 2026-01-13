import 'package:sqflite/sqflite.dart';
import 'package:progressly/data/models/models.dart';
import 'progressly_database.dart';

class TaskDao {
  final _database = ProgresslyDatabase();

  Future<int> insertTask(TaskModel task) async {
    final db = await _database.database;
    return db.insert('tasks', {
      'title': task.title,
      'description': task.description,
      'isCompleted': task.isCompleted ? 1 : 0,
      'completedDate': task.completedDate?.toString(),
      'createdDate': task.createdDate.toString(),
      'category': task.category,
    });
  }

  Future<List<TaskModel>> getAllTasks() async {
    final db = await _database.database;
    final maps = await db.query('tasks', orderBy: 'createdDate DESC');
    return List.generate(maps.length, (i) => TaskModel.fromJson(maps[i]));
  }

  Future<TaskModel?> getTaskById(int id) async {
    final db = await _database.database;
    final maps = await db.query('tasks', where: 'id = ?', whereArgs: [id]);
    if (maps.isNotEmpty) {
      return TaskModel.fromJson(maps.first);
    }
    return null;
  }

  Future<int> updateTask(TaskModel task) async {
    final db = await _database.database;
    return db.update('tasks', {
      'title': task.title,
      'description': task.description,
      'isCompleted': task.isCompleted ? 1 : 0,
      'completedDate': task.completedDate?.toString(),
      'category': task.category,
    }, where: 'id = ?', whereArgs: [task.id]);
  }

  Future<int> deleteTask(int id) async {
    final db = await _database.database;
    return db.delete('tasks', where: 'id = ?', whereArgs: [id]);
  }

  Future<List<TaskModel>> getTasksByDate(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final maps = await db.query('tasks', where: 'DATE(createdDate) = ?', whereArgs: [dateStr]);
    return List.generate(maps.length, (i) => TaskModel.fromJson(maps[i]));
  }

  Future<int> getCompletedTasksCount(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final result = await db.rawQuery(
      'SELECT COUNT(*) as count FROM tasks WHERE isCompleted = 1 AND DATE(completedDate) = ?',
      [dateStr],
    );
    return Sqflite.firstIntValue(result) ?? 0;
  }
}

class MealDao {
  final _database = ProgresslyDatabase();

  Future<int> insertMeal(MealModel meal) async {
    final db = await _database.database;
    return db.insert('meals', {
      'name': meal.name,
      'calories': meal.calories,
      'mealType': meal.mealType,
      'loggedDate': meal.loggedDate.toString(),
      'time': meal.time,
    });
  }

  Future<List<MealModel>> getAllMeals() async {
    final db = await _database.database;
    final maps = await db.query('meals', orderBy: 'loggedDate DESC');
    return List.generate(maps.length, (i) => MealModel.fromJson(maps[i]));
  }

  Future<MealModel?> getMealById(int id) async {
    final db = await _database.database;
    final maps = await db.query('meals', where: 'id = ?', whereArgs: [id]);
    if (maps.isNotEmpty) {
      return MealModel.fromJson(maps.first);
    }
    return null;
  }

  Future<int> updateMeal(MealModel meal) async {
    final db = await _database.database;
    return db.update('meals', {
      'name': meal.name,
      'calories': meal.calories,
      'mealType': meal.mealType,
      'loggedDate': meal.loggedDate.toString(),
      'time': meal.time,
    }, where: 'id = ?', whereArgs: [meal.id]);
  }

  Future<int> deleteMeal(int id) async {
    final db = await _database.database;
    return db.delete('meals', where: 'id = ?', whereArgs: [id]);
  }

  Future<List<MealModel>> getMealsByDate(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final maps = await db.query('meals', where: 'DATE(loggedDate) = ?', whereArgs: [dateStr]);
    return List.generate(maps.length, (i) => MealModel.fromJson(maps[i]));
  }

  Future<int> getTotalCaloriesByDate(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final result = await db.rawQuery(
      'SELECT SUM(calories) as total FROM meals WHERE DATE(loggedDate) = ?',
      [dateStr],
    );
    return Sqflite.firstIntValue(result) ?? 0;
  }
}

class WaterLogDao {
  final _database = ProgresslyDatabase();

  Future<int> insertWaterLog(WaterLogModel log) async {
    final db = await _database.database;
    return db.insert('water_logs', {
      'amount': log.amount,
      'loggedDate': log.loggedDate.toString(),
      'time': log.time,
    });
  }

  Future<List<WaterLogModel>> getAllWaterLogs() async {
    final db = await _database.database;
    final maps = await db.query('water_logs', orderBy: 'loggedDate DESC');
    return List.generate(maps.length, (i) => WaterLogModel.fromJson(maps[i]));
  }

  Future<WaterLogModel?> getWaterLogById(int id) async {
    final db = await _database.database;
    final maps = await db.query('water_logs', where: 'id = ?', whereArgs: [id]);
    if (maps.isNotEmpty) {
      return WaterLogModel.fromJson(maps.first);
    }
    return null;
  }

  Future<int> updateWaterLog(WaterLogModel log) async {
    final db = await _database.database;
    return db.update('water_logs', {
      'amount': log.amount,
      'loggedDate': log.loggedDate.toString(),
      'time': log.time,
    }, where: 'id = ?', whereArgs: [log.id]);
  }

  Future<int> deleteWaterLog(int id) async {
    final db = await _database.database;
    return db.delete('water_logs', where: 'id = ?', whereArgs: [id]);
  }

  Future<List<WaterLogModel>> getWaterLogsByDate(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final maps = await db.query('water_logs', where: 'DATE(loggedDate) = ?', whereArgs: [dateStr]);
    return List.generate(maps.length, (i) => WaterLogModel.fromJson(maps[i]));
  }

  Future<int> getTotalWaterByDate(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final result = await db.rawQuery(
      'SELECT SUM(amount) as total FROM water_logs WHERE DATE(loggedDate) = ?',
      [dateStr],
    );
    return Sqflite.firstIntValue(result) ?? 0;
  }
}

class HabitDao {
  final _database = ProgresslyDatabase();

  Future<int> insertHabit(HabitModel habit) async {
    final db = await _database.database;
    return db.insert('habits', {
      'name': habit.name,
      'description': habit.description,
      'frequency': habit.frequency,
      'createdDate': habit.createdDate.toString(),
      'category': habit.category,
    });
  }

  Future<List<HabitModel>> getAllHabits() async {
    final db = await _database.database;
    final maps = await db.query('habits', orderBy: 'createdDate DESC');
    return List.generate(maps.length, (i) => HabitModel.fromJson(maps[i]));
  }

  Future<HabitModel?> getHabitById(int id) async {
    final db = await _database.database;
    final maps = await db.query('habits', where: 'id = ?', whereArgs: [id]);
    if (maps.isNotEmpty) {
      return HabitModel.fromJson(maps.first);
    }
    return null;
  }

  Future<int> updateHabit(HabitModel habit) async {
    final db = await _database.database;
    return db.update('habits', {
      'name': habit.name,
      'description': habit.description,
      'frequency': habit.frequency,
      'category': habit.category,
    }, where: 'id = ?', whereArgs: [habit.id]);
  }

  Future<int> deleteHabit(int id) async {
    final db = await _database.database;
    final db_instance = await _database.database;
    await db_instance.delete('habit_logs', where: 'habitId = ?', whereArgs: [id]);
    await db_instance.delete('habit_streaks', where: 'habitId = ?', whereArgs: [id]);
    return db.delete('habits', where: 'id = ?', whereArgs: [id]);
  }

  Future<int> insertHabitLog(int habitId, DateTime date) async {
    final db = await _database.database;
    return db.insert('habit_logs', {
      'habitId': habitId,
      'completedDate': date.toString(),
    });
  }

  Future<List<DateTime>> getHabitLogs(int habitId) async {
    final db = await _database.database;
    final maps = await db.query('habit_logs', where: 'habitId = ?', whereArgs: [habitId]);
    return List.generate(
      maps.length,
      (i) => DateTime.parse(maps[i]['completedDate'] as String),
    );
  }

  Future<bool> isHabitCompletedToday(int habitId) async {
    final db = await _database.database;
    final today = DateTime.now();
    final dateStr = today.toString().split(' ')[0];
    final maps = await db.query(
      'habit_logs',
      where: 'habitId = ? AND DATE(completedDate) = ?',
      whereArgs: [habitId, dateStr],
    );
    return maps.isNotEmpty;
  }

  Future<int> getCompletedHabitsCount(DateTime date) async {
    final db = await _database.database;
    final dateStr = date.toString().split(' ')[0];
    final result = await db.rawQuery(
      'SELECT COUNT(DISTINCT habitId) as count FROM habit_logs WHERE DATE(completedDate) = ?',
      [dateStr],
    );
    return Sqflite.firstIntValue(result) ?? 0;
  }
}
