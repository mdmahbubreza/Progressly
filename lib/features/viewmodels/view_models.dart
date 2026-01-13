import 'package:get/get.dart';
import 'package:progressly/data/models/models.dart';
import 'package:progressly/data/repositories/repositories.dart';

class TaskViewModel extends GetxController {
  final taskRepository = TaskRepository();
  
  final tasks = <TaskModel>[].obs;
  final isLoading = false.obs;
  final todayCompletedCount = 0.obs;

  @override
  void onInit() {
    super.onInit();
    loadTasks();
    loadTodayStats();
  }

  Future<void> loadTasks() async {
    isLoading.value = true;
    try {
      final loadedTasks = await taskRepository.getTasks();
      tasks.assignAll(loadedTasks);
    } catch (e) {
      Get.snackbar('Error', 'Failed to load tasks: $e');
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> loadTodayStats() async {
    try {
      final count = await taskRepository.getTodayCompletedCount();
      todayCompletedCount.value = count;
    } catch (e) {
      Get.snackbar('Error', 'Failed to load stats: $e');
    }
  }

  Future<void> addTask(TaskModel task) async {
    try {
      final id = await taskRepository.addTask(task);
      final newTask = task.copyWith(id: id);
      tasks.add(newTask);
      Get.snackbar('Success', 'Task added successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to add task: $e');
    }
  }

  Future<void> updateTask(TaskModel task) async {
    try {
      await taskRepository.updateTask(task);
      final index = tasks.indexWhere((t) => t.id == task.id);
      if (index != -1) {
        tasks[index] = task;
        tasks.refresh();
      }
      await loadTodayStats();
      Get.snackbar('Success', 'Task updated successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to update task: $e');
    }
  }

  Future<void> toggleTaskCompletion(TaskModel task) async {
    final updated = task.copyWith(
      isCompleted: !task.isCompleted,
      completedDate: !task.isCompleted ? DateTime.now() : null,
    );
    await updateTask(updated);
  }

  Future<void> deleteTask(int id) async {
    try {
      await taskRepository.deleteTask(id);
      tasks.removeWhere((t) => t.id == id);
      Get.snackbar('Success', 'Task deleted successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to delete task: $e');
    }
  }
}

class MealViewModel extends GetxController {
  final mealRepository = MealRepository();

  final meals = <MealModel>[].obs;
  final isLoading = false.obs;
  final todayTotalCalories = 0.obs;
  final todayMealsCount = 0.obs;

  @override
  void onInit() {
    super.onInit();
    loadMeals();
    loadTodayStats();
  }

  Future<void> loadMeals() async {
    isLoading.value = true;
    try {
      final loadedMeals = await mealRepository.getMeals();
      meals.assignAll(loadedMeals);
    } catch (e) {
      Get.snackbar('Error', 'Failed to load meals: $e');
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> loadTodayStats() async {
    try {
      final calories = await mealRepository.getTodayTotalCalories();
      final count = await mealRepository.getMealsCountByDate(DateTime.now());
      todayTotalCalories.value = calories;
      todayMealsCount.value = count;
    } catch (e) {
      Get.snackbar('Error', 'Failed to load stats: $e');
    }
  }

  Future<void> addMeal(MealModel meal) async {
    try {
      final id = await mealRepository.addMeal(meal);
      final newMeal = meal.copyWith(id: id);
      meals.add(newMeal);
      await loadTodayStats();
      Get.snackbar('Success', 'Meal logged successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to add meal: $e');
    }
  }

  Future<void> updateMeal(MealModel meal) async {
    try {
      await mealRepository.updateMeal(meal);
      final index = meals.indexWhere((m) => m.id == meal.id);
      if (index != -1) {
        meals[index] = meal;
        meals.refresh();
      }
      await loadTodayStats();
      Get.snackbar('Success', 'Meal updated successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to update meal: $e');
    }
  }

  Future<void> deleteMeal(int id) async {
    try {
      await mealRepository.deleteMeal(id);
      meals.removeWhere((m) => m.id == id);
      await loadTodayStats();
      Get.snackbar('Success', 'Meal deleted successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to delete meal: $e');
    }
  }
}

class WaterViewModel extends GetxController {
  final waterRepository = WaterRepository();

  final waterLogs = <WaterLogModel>[].obs;
  final isLoading = false.obs;
  final todayTotalWater = 0.obs;
  final dailyGoal = 2000.obs;

  @override
  void onInit() {
    super.onInit();
    loadWaterLogs();
    loadTodayStats();
  }

  Future<void> loadWaterLogs() async {
    isLoading.value = true;
    try {
      final logs = await waterRepository.getWaterLogs();
      waterLogs.assignAll(logs);
    } catch (e) {
      Get.snackbar('Error', 'Failed to load water logs: $e');
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> loadTodayStats() async {
    try {
      final total = await waterRepository.getTodayTotalWater();
      todayTotalWater.value = total;
    } catch (e) {
      Get.snackbar('Error', 'Failed to load stats: $e');
    }
  }

  Future<void> logWater(int amount) async {
    try {
      final log = WaterLogModel(amount: amount);
      await waterRepository.addWaterLog(log);
      waterLogs.add(log);
      await loadTodayStats();
      Get.snackbar('Success', 'Water logged successfully! 💧');
    } catch (e) {
      Get.snackbar('Error', 'Failed to log water: $e');
    }
  }

  Future<void> quickLog(int amount) async {
    await logWater(amount);
  }

  double getProgressPercentage() {
    return (todayTotalWater.value / dailyGoal.value).clamp(0.0, 1.0);
  }

  int getRemainingWater() {
    final remaining = dailyGoal.value - todayTotalWater.value;
    return remaining > 0 ? remaining : 0;
  }

  Future<void> deleteWaterLog(int id) async {
    try {
      await waterRepository.deleteWaterLog(id);
      waterLogs.removeWhere((log) => log.id == id);
      await loadTodayStats();
      Get.snackbar('Success', 'Water log deleted successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to delete water log: $e');
    }
  }
}

class HabitViewModel extends GetxController {
  final habitRepository = HabitRepository();

  final habits = <HabitModel>[].obs;
  final completedToday = <int, bool>{}.obs;
  final isLoading = false.obs;
  final todayCompletedCount = 0.obs;

  @override
  void onInit() {
    super.onInit();
    loadHabits();
    loadTodayStats();
  }

  Future<void> loadHabits() async {
    isLoading.value = true;
    try {
      final loadedHabits = await habitRepository.getHabits();
      habits.assignAll(loadedHabits);
      
      // Check which habits are completed today
      for (var habit in habits) {
        final isCompleted = await habitRepository.isHabitCompletedToday(habit.id!);
        completedToday[habit.id!] = isCompleted;
      }
    } catch (e) {
      Get.snackbar('Error', 'Failed to load habits: $e');
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> loadTodayStats() async {
    try {
      final count = await habitRepository.getTodayCompletedCount();
      todayCompletedCount.value = count;
    } catch (e) {
      Get.snackbar('Error', 'Failed to load stats: $e');
    }
  }

  Future<void> addHabit(HabitModel habit) async {
    try {
      final id = await habitRepository.addHabit(habit);
      final newHabit = habit.copyWith(id: id);
      habits.add(newHabit);
      completedToday[id] = false;
      Get.snackbar('Success', 'Habit added successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to add habit: $e');
    }
  }

  Future<void> updateHabit(HabitModel habit) async {
    try {
      await habitRepository.updateHabit(habit);
      final index = habits.indexWhere((h) => h.id == habit.id);
      if (index != -1) {
        habits[index] = habit;
        habits.refresh();
      }
      Get.snackbar('Success', 'Habit updated successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to update habit: $e');
    }
  }

  Future<void> completeHabit(HabitModel habit) async {
    try {
      await habitRepository.logHabitCompletion(habit.id!);
      completedToday[habit.id!] = true;
      completedToday.refresh();
      await loadTodayStats();
      Get.snackbar('Success', 'Habit completed! 🎉');
    } catch (e) {
      Get.snackbar('Error', 'Failed to complete habit: $e');
    }
  }

  Future<void> deleteHabit(int id) async {
    try {
      await habitRepository.deleteHabit(id);
      habits.removeWhere((h) => h.id == id);
      completedToday.remove(id);
      Get.snackbar('Success', 'Habit deleted successfully');
    } catch (e) {
      Get.snackbar('Error', 'Failed to delete habit: $e');
    }
  }

  bool isHabitCompletedToday(int habitId) {
    return completedToday[habitId] ?? false;
  }
}

class ProfileViewModel extends GetxController {
  final taskRepository = TaskRepository();
  final mealRepository = MealRepository();
  final waterRepository = WaterRepository();
  final habitRepository = HabitRepository();

  final userProfile = Rx<UserProfile?>(null);
  final isLoading = false.obs;

  @override
  void onInit() {
    super.onInit();
    loadProfile();
  }

  Future<void> loadProfile() async {
    // Load profile from storage
  }

  Future<DailySummary> getDailySummary(DateTime date) async {
    try {
      final tasksCompleted = await taskRepository.getCompletedTasksCount(date);
      final mealsLogged = await mealRepository.getMealsCountByDate(date);
      final waterIntake = await waterRepository.getTotalWaterByDate(date);
      final habitsCompleted = await habitRepository.getCompletedHabitsCount(date);
      final totalCalories = await mealRepository.getTotalCaloriesByDate(date);

      return DailySummary(
        date: date,
        tasksCompleted: tasksCompleted,
        mealsLogged: mealsLogged,
        waterIntake: waterIntake,
        habitsCompleted: habitsCompleted,
        totalCalories: totalCalories,
      );
    } catch (e) {
      Get.snackbar('Error', 'Failed to load daily summary: $e');
      return DailySummary(date: date);
    }
  }
}
