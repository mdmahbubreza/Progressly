import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/data/models/models.dart';

class HabitsScreen extends StatelessWidget {
  const HabitsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<HabitViewModel>();

    return Scaffold(
      body: Obx(() {
        if (viewModel.isLoading.value) {
          return Center(child: CircularProgressIndicator());
        }

        if (viewModel.habits.isEmpty) {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  '✨ No habits yet',
                  style: Theme.of(context).textTheme.headlineMedium,
                ),
                SizedBox(height: 8),
                Text(
                  'Create a habit to build consistency',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
              ],
            ),
          );
        }

        return ListView(
          padding: EdgeInsets.all(16),
          children: [
            // Today's Progress
            Card(
              child: Padding(
                padding: EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Today\'s Progress',
                      style: Theme.of(context).textTheme.titleLarge,
                    ),
                    SizedBox(height: 8),
                    Obx(
                      () => Text(
                        '${viewModel.todayCompletedCount.value} / ${viewModel.habits.length} habits completed',
                        style: Theme.of(context).textTheme.bodyLarge,
                      ),
                    ),
                    SizedBox(height: 16),
                    ClipRRect(
                      borderRadius: BorderRadius.circular(8),
                      child: Obx(
                        () => LinearProgressIndicator(
                          value: viewModel.habits.isEmpty
                              ? 0
                              : viewModel.todayCompletedCount.value /
                                  viewModel.habits.length,
                          minHeight: 8,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(height: 24),
            // Habits List
            ...viewModel.habits.map((habit) => _buildHabitCard(context, habit, viewModel)),
          ],
        );
      }),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showAddHabitDialog(context),
        child: Icon(Icons.add),
      ),
    );
  }

  Widget _buildHabitCard(
    BuildContext context,
    HabitModel habit,
    HabitViewModel viewModel,
  ) {
    final isCompleted = viewModel.isHabitCompletedToday(habit.id!);

    return Card(
      margin: EdgeInsets.only(bottom: 12),
      child: ListTile(
        leading: Checkbox(
          value: isCompleted,
          onChanged: (_) {
            if (!isCompleted) {
              viewModel.completeHabit(habit);
            }
          },
        ),
        title: Text(habit.name),
        subtitle: Text('${habit.frequency} • ${habit.category}'),
        trailing: PopupMenuButton(
          itemBuilder: (context) => [
            PopupMenuItem(
              child: Text('Delete'),
              onTap: () => viewModel.deleteHabit(habit.id!),
            ),
          ],
        ),
      ),
    );
  }

  void _showAddHabitDialog(BuildContext context) {
    final nameController = TextEditingController();
    final descController = TextEditingController();
    String selectedFrequency = 'Daily';

    Get.dialog(
      StatefulBuilder(
        builder: (context, setState) => AlertDialog(
          title: Text('Add Habit'),
          content: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextField(
                  controller: nameController,
                  decoration: InputDecoration(labelText: 'Habit Name'),
                ),
                SizedBox(height: 16),
                TextField(
                  controller: descController,
                  decoration: InputDecoration(labelText: 'Description (optional)'),
                ),
                SizedBox(height: 16),
                DropdownButtonFormField<String>(
                  value: selectedFrequency,
                  items: ['Daily', 'Weekly', 'Monthly']
                      .map(
                        (freq) => DropdownMenuItem(
                          value: freq,
                          child: Text(freq),
                        ),
                      )
                      .toList(),
                  onChanged: (value) {
                    setState(() => selectedFrequency = value ?? 'Daily');
                  },
                  decoration: InputDecoration(labelText: 'Frequency'),
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
                  final habit = HabitModel(
                    name: nameController.text,
                    description: descController.text,
                    frequency: selectedFrequency,
                  );
                  Get.find<HabitViewModel>().addHabit(habit);
                  Get.back();
                }
              },
              child: Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
