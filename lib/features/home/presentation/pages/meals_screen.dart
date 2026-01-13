import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/data/models/models.dart';

class MealsScreen extends StatelessWidget {
  const MealsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<MealViewModel>();

    return Scaffold(
      body: Obx(() {
        if (viewModel.isLoading.value) {
          return Center(child: CircularProgressIndicator());
        }

        return ListView(
          padding: EdgeInsets.all(16),
          children: [
            // Daily Summary Card
            Card(
              child: Padding(
                padding: EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Today\'s Summary',
                      style: Theme.of(context).textTheme.titleLarge,
                    ),
                    SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        _buildStatColumn(
                          context,
                          'Meals',
                          viewModel.todayMealsCount.toString(),
                        ),
                        _buildStatColumn(
                          context,
                          'Calories',
                          '${viewModel.todayTotalCalories}',
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(height: 24),
            // Meals List
            if (viewModel.meals.isEmpty)
              Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      '🍽️ No meals logged',
                      style: Theme.of(context).textTheme.headlineMedium,
                    ),
                    SizedBox(height: 8),
                    Text(
                      'Log a meal to track your nutrition',
                      style: Theme.of(context).textTheme.bodyMedium,
                    ),
                  ],
                ),
              )
            else
              ...viewModel.meals.map(
                (meal) => Card(
                  margin: EdgeInsets.only(bottom: 12),
                  child: ListTile(
                    leading: Icon(Icons.restaurant),
                    title: Text(meal.name),
                    subtitle: Text('${meal.mealType} • ${meal.calories} cal'),
                    trailing: IconButton(
                      icon: Icon(Icons.delete_outline),
                      onPressed: () => viewModel.deleteMeal(meal.id!),
                    ),
                  ),
                ),
              ),
          ],
        );
      }),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showAddMealDialog(context),
        child: Icon(Icons.add),
      ),
    );
  }

  Widget _buildStatColumn(BuildContext context, String label, String value) {
    return Column(
      children: [
        Text(
          value,
          style: Theme.of(context).textTheme.headlineMedium,
        ),
        SizedBox(height: 4),
        Text(
          label,
          style: Theme.of(context).textTheme.bodySmall,
        ),
      ],
    );
  }

  void _showAddMealDialog(BuildContext context) {
    final nameController = TextEditingController();
    final caloriesController = TextEditingController();
    String selectedMealType = 'Breakfast';

    Get.dialog(
      StatefulBuilder(
        builder: (context, setState) => AlertDialog(
          title: Text('Log Meal'),
          content: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextField(
                  controller: nameController,
                  decoration: InputDecoration(labelText: 'Meal Name'),
                ),
                SizedBox(height: 16),
                TextField(
                  controller: caloriesController,
                  keyboardType: TextInputType.number,
                  decoration: InputDecoration(labelText: 'Calories'),
                ),
                SizedBox(height: 16),
                DropdownButtonFormField<String>(
                  value: selectedMealType,
                  items: ['Breakfast', 'Lunch', 'Dinner', 'Snack']
                      .map(
                        (type) => DropdownMenuItem(
                          value: type,
                          child: Text(type),
                        ),
                      )
                      .toList(),
                  onChanged: (value) {
                    setState(() => selectedMealType = value ?? 'Breakfast');
                  },
                  decoration: InputDecoration(labelText: 'Meal Type'),
                ),
              ],
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Get.back(),
              child: Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                if (nameController.text.isNotEmpty) {
                  final meal = MealModel(
                    name: nameController.text,
                    calories:
                        int.tryParse(caloriesController.text) ?? 0,
                    mealType: selectedMealType,
                  );
                  Get.find<MealViewModel>().addMeal(meal);
                  Get.back();
                }
              },
              child: Text('Log'),
            ),
          ],
        ),
      ),
    );
  }
}
