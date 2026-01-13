class UserProfile {
  final String name;
  final int age;
  final String gender;
  final int weight;
  final bool notificationsEnabled;

  UserProfile({
    required this.name,
    required this.age,
    required this.gender,
    required this.weight,
    this.notificationsEnabled = true,
  });

  Map<String, dynamic> toJson() => {
    'name': name,
    'age': age,
    'gender': gender,
    'weight': weight,
    'notificationsEnabled': notificationsEnabled,
  };

  factory UserProfile.fromJson(Map<String, dynamic> json) => UserProfile(
    name: json['name'],
    age: json['age'],
    gender: json['gender'],
    weight: json['weight'],
    notificationsEnabled: json['notificationsEnabled'] ?? true,
  );
}

class TaskModel {
  final int? id;
  final String title;
  final String description;
  final bool isCompleted;
  final DateTime? completedDate;
  final DateTime createdDate;
  final String category;

  TaskModel({
    this.id,
    required this.title,
    this.description = '',
    this.isCompleted = false,
    this.completedDate,
    DateTime? createdDate,
    this.category = 'General',
  }) : createdDate = createdDate ?? DateTime.now();

  Map<String, dynamic> toJson() => {
    'id': id,
    'title': title,
    'description': description,
    'isCompleted': isCompleted ? 1 : 0,
    'completedDate': completedDate?.toString(),
    'createdDate': createdDate.toString(),
    'category': category,
  };

  factory TaskModel.fromJson(Map<String, dynamic> json) => TaskModel(
    id: json['id'],
    title: json['title'],
    description: json['description'] ?? '',
    isCompleted: json['isCompleted'] == 1,
    completedDate: json['completedDate'] != null ? DateTime.parse(json['completedDate']) : null,
    createdDate: DateTime.parse(json['createdDate']),
    category: json['category'] ?? 'General',
  );

  TaskModel copyWith({
    int? id,
    String? title,
    String? description,
    bool? isCompleted,
    DateTime? completedDate,
    DateTime? createdDate,
    String? category,
  }) => TaskModel(
    id: id ?? this.id,
    title: title ?? this.title,
    description: description ?? this.description,
    isCompleted: isCompleted ?? this.isCompleted,
    completedDate: completedDate ?? this.completedDate,
    createdDate: createdDate ?? this.createdDate,
    category: category ?? this.category,
  );
}

class MealModel {
  final int? id;
  final String name;
  final int calories;
  final String mealType; // Breakfast, Lunch, Dinner, Snack
  final DateTime loggedDate;
  final String time;

  MealModel({
    this.id,
    required this.name,
    this.calories = 0,
    this.mealType = 'Breakfast',
    DateTime? loggedDate,
    this.time = '',
  }) : loggedDate = loggedDate ?? DateTime.now();

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'calories': calories,
    'mealType': mealType,
    'loggedDate': loggedDate.toString(),
    'time': time,
  };

  factory MealModel.fromJson(Map<String, dynamic> json) => MealModel(
    id: json['id'],
    name: json['name'],
    calories: json['calories'] ?? 0,
    mealType: json['mealType'] ?? 'Breakfast',
    loggedDate: DateTime.parse(json['loggedDate']),
    time: json['time'] ?? '',
  );

  MealModel copyWith({
    int? id,
    String? name,
    int? calories,
    String? mealType,
    DateTime? loggedDate,
    String? time,
  }) => MealModel(
    id: id ?? this.id,
    name: name ?? this.name,
    calories: calories ?? this.calories,
    mealType: mealType ?? this.mealType,
    loggedDate: loggedDate ?? this.loggedDate,
    time: time ?? this.time,
  );
}

class WaterLogModel {
  final int? id;
  final int amount; // in ml
  final DateTime loggedDate;
  final String time;

  WaterLogModel({
    this.id,
    required this.amount,
    DateTime? loggedDate,
    this.time = '',
  }) : loggedDate = loggedDate ?? DateTime.now();

  Map<String, dynamic> toJson() => {
    'id': id,
    'amount': amount,
    'loggedDate': loggedDate.toString(),
    'time': time,
  };

  factory WaterLogModel.fromJson(Map<String, dynamic> json) => WaterLogModel(
    id: json['id'],
    amount: json['amount'],
    loggedDate: DateTime.parse(json['loggedDate']),
    time: json['time'] ?? '',
  );

  WaterLogModel copyWith({
    int? id,
    int? amount,
    DateTime? loggedDate,
    String? time,
  }) => WaterLogModel(
    id: id ?? this.id,
    amount: amount ?? this.amount,
    loggedDate: loggedDate ?? this.loggedDate,
    time: time ?? this.time,
  );
}

class HabitModel {
  final int? id;
  final String name;
  final String description;
  final String frequency; // Daily, Weekly, Monthly
  final DateTime createdDate;
  final String category;

  HabitModel({
    this.id,
    required this.name,
    this.description = '',
    this.frequency = 'Daily',
    DateTime? createdDate,
    this.category = 'General',
  }) : createdDate = createdDate ?? DateTime.now();

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'description': description,
    'frequency': frequency,
    'createdDate': createdDate.toString(),
    'category': category,
  };

  factory HabitModel.fromJson(Map<String, dynamic> json) => HabitModel(
    id: json['id'],
    name: json['name'],
    description: json['description'] ?? '',
    frequency: json['frequency'] ?? 'Daily',
    createdDate: DateTime.parse(json['createdDate']),
    category: json['category'] ?? 'General',
  );

  HabitModel copyWith({
    int? id,
    String? name,
    String? description,
    String? frequency,
    DateTime? createdDate,
    String? category,
  }) => HabitModel(
    id: id ?? this.id,
    name: name ?? this.name,
    description: description ?? this.description,
    frequency: frequency ?? this.frequency,
    createdDate: createdDate ?? this.createdDate,
    category: category ?? this.category,
  );
}

class StreakData {
  final int dailyStreak;
  final int weeklyStreak;
  final int longestStreak;

  StreakData({
    this.dailyStreak = 0,
    this.weeklyStreak = 0,
    this.longestStreak = 0,
  });

  Map<String, dynamic> toJson() => {
    'dailyStreak': dailyStreak,
    'weeklyStreak': weeklyStreak,
    'longestStreak': longestStreak,
  };

  factory StreakData.fromJson(Map<String, dynamic> json) => StreakData(
    dailyStreak: json['dailyStreak'] ?? 0,
    weeklyStreak: json['weeklyStreak'] ?? 0,
    longestStreak: json['longestStreak'] ?? 0,
  );
}

class DailySummary {
  final DateTime date;
  final int tasksCompleted;
  final int mealsLogged;
  final int waterIntake;
  final int habitsCompleted;
  final int totalCalories;

  DailySummary({
    DateTime? date,
    this.tasksCompleted = 0,
    this.mealsLogged = 0,
    this.waterIntake = 0,
    this.habitsCompleted = 0,
    this.totalCalories = 0,
  }) : date = date ?? DateTime.now();

  Map<String, dynamic> toJson() => {
    'date': date.toString(),
    'tasksCompleted': tasksCompleted,
    'mealsLogged': mealsLogged,
    'waterIntake': waterIntake,
    'habitsCompleted': habitsCompleted,
    'totalCalories': totalCalories,
  };

  factory DailySummary.fromJson(Map<String, dynamic> json) => DailySummary(
    date: DateTime.parse(json['date']),
    tasksCompleted: json['tasksCompleted'] ?? 0,
    mealsLogged: json['mealsLogged'] ?? 0,
    waterIntake: json['waterIntake'] ?? 0,
    habitsCompleted: json['habitsCompleted'] ?? 0,
    totalCalories: json['totalCalories'] ?? 0,
  );
}
