import 'package:progressly/data/models/models.dart';
import 'package:progressly/data/database/dao.dart';

class TaskRepository {
  final taskDao = TaskDao();

  Future<int> addTask(TaskModel task) => taskDao.insertTask(task);

  Future<List<TaskModel>> getTasks() => taskDao.getAllTasks();

  Future<TaskModel?> getTask(int id) => taskDao.getTaskById(id);

  Future<int> updateTask(TaskModel task) => taskDao.updateTask(task);

  Future<int> deleteTask(int id) => taskDao.deleteTask(id);

  Future<List<TaskModel>> getTasksByDate(DateTime date) => taskDao.getTasksByDate(date);

  Future<int> getCompletedTasksCount(DateTime date) => taskDao.getCompletedTasksCount(date);

  Future<int> getTodayCompletedCount() => getCompletedTasksCount(DateTime.now());
}

class MealRepository {
  final mealDao = MealDao();

  Future<int> addMeal(MealModel meal) => mealDao.insertMeal(meal);

  Future<List<MealModel>> getMeals() => mealDao.getAllMeals();

  Future<MealModel?> getMeal(int id) => mealDao.getMealById(id);

  Future<int> updateMeal(MealModel meal) => mealDao.updateMeal(meal);

  Future<int> deleteMeal(int id) => mealDao.deleteMeal(id);

  Future<List<MealModel>> getMealsByDate(DateTime date) => mealDao.getMealsByDate(date);

  Future<int> getTotalCaloriesByDate(DateTime date) => mealDao.getTotalCaloriesByDate(date);

  Future<int> getTodayTotalCalories() => getTotalCaloriesByDate(DateTime.now());

  Future<int> getMealsCountByDate(DateTime date) async {
    final meals = await mealDao.getMealsByDate(date);
    return meals.length;
  }
}

class WaterRepository {
  final waterLogDao = WaterLogDao();

  Future<int> addWaterLog(WaterLogModel log) => waterLogDao.insertWaterLog(log);

  Future<List<WaterLogModel>> getWaterLogs() => waterLogDao.getAllWaterLogs();

  Future<WaterLogModel?> getWaterLog(int id) => waterLogDao.getWaterLogById(id);

  Future<int> updateWaterLog(WaterLogModel log) => waterLogDao.updateWaterLog(log);

  Future<int> deleteWaterLog(int id) => waterLogDao.deleteWaterLog(id);

  Future<List<WaterLogModel>> getWaterLogsByDate(DateTime date) => waterLogDao.getWaterLogsByDate(date);

  Future<int> getTotalWaterByDate(DateTime date) => waterLogDao.getTotalWaterByDate(date);

  Future<int> getTodayTotalWater() => getTotalWaterByDate(DateTime.now());

  Future<void> logQuickWater(int amount) async {
    final log = WaterLogModel(amount: amount);
    await waterLogDao.insertWaterLog(log);
  }
}

class HabitRepository {
  final habitDao = HabitDao();

  Future<int> addHabit(HabitModel habit) => habitDao.insertHabit(habit);

  Future<List<HabitModel>> getHabits() => habitDao.getAllHabits();

  Future<HabitModel?> getHabit(int id) => habitDao.getHabitById(id);

  Future<int> updateHabit(HabitModel habit) => habitDao.updateHabit(habit);

  Future<int> deleteHabit(int id) => habitDao.deleteHabit(id);

  Future<int> logHabitCompletion(int habitId) => habitDao.insertHabitLog(habitId, DateTime.now());

  Future<List<DateTime>> getHabitLogs(int habitId) => habitDao.getHabitLogs(habitId);

  Future<bool> isHabitCompletedToday(int habitId) => habitDao.isHabitCompletedToday(habitId);

  Future<int> getCompletedHabitsCount(DateTime date) => habitDao.getCompletedHabitsCount(date);

  Future<int> getTodayCompletedCount() => getCompletedHabitsCount(DateTime.now());
}
