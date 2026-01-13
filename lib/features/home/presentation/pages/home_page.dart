import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/features/home/presentation/pages/tasks_screen.dart';
import 'package:progressly/features/home/presentation/pages/water_screen.dart';
import 'package:progressly/features/home/presentation/pages/meals_screen.dart';
import 'package:progressly/features/home/presentation/pages/habits_screen.dart';
import 'package:progressly/features/home/presentation/pages/profile_screen.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _currentIndex = 0;

  final List<Widget> _screens = [
    TasksScreen(),
    WaterScreen(),
    MealsScreen(),
    HabitsScreen(),
    ProfileScreen(),
  ];

  final List<String> _titles = ['Tasks', 'Water', 'Meals', 'Habits', 'Profile'];

  @override
  void initState() {
    super.initState();
    // Initialize all ViewModels
    Get.put(TaskViewModel());
    Get.put(WaterViewModel());
    Get.put(MealViewModel());
    Get.put(HabitViewModel());
    Get.put(ProfileViewModel());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_titles[_currentIndex]),
        elevation: 0,
      ),
      body: _screens[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        type: BottomNavigationBarType.fixed,
        onTap: (index) {
          setState(() => _currentIndex = index);
        },
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.check_circle_outline),
            activeIcon: Icon(Icons.check_circle),
            label: 'Tasks',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.local_drink_outlined),
            activeIcon: Icon(Icons.local_drink),
            label: 'Water',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.restaurant_outlined),
            activeIcon: Icon(Icons.restaurant),
            label: 'Meals',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.favorite_outline),
            activeIcon: Icon(Icons.favorite),
            label: 'Habits',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person_outline),
            activeIcon: Icon(Icons.person),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}
