import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/data/models/models.dart';

class TasksScreen extends StatelessWidget {
  const TasksScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<TaskViewModel>();

    return Scaffold(
      body: Obx(() {
        if (viewModel.isLoading.value) {
          return Center(child: CircularProgressIndicator());
        }

        if (viewModel.tasks.isEmpty) {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  '✅ No tasks yet',
                  style: Theme.of(context).textTheme.headlineMedium,
                ),
                SizedBox(height: 8),
                Text(
                  'Create a task to get started',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
              ],
            ),
          );
        }

        return ListView(
          padding: EdgeInsets.all(16),
          children: [
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
                        '${viewModel.todayCompletedCount.value} tasks completed',
                        style: Theme.of(context).textTheme.bodyLarge,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(height: 16),
            ...viewModel.tasks.map((task) => _buildTaskCard(context, task, viewModel)),
          ],
        );
      }),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showAddTaskDialog(context, viewModel),
        child: Icon(Icons.add),
      ),
    );
  }

  Widget _buildTaskCard(BuildContext context, TaskModel task, TaskViewModel viewModel) {
    return Card(
      margin: EdgeInsets.only(bottom: 12),
      child: ListTile(
        leading: Checkbox(
          value: task.isCompleted,
          onChanged: (_) => viewModel.toggleTaskCompletion(task),
        ),
        title: Text(
          task.title,
          style: TextStyle(
            decoration: task.isCompleted ? TextDecoration.lineThrough : null,
          ),
        ),
        subtitle: task.description.isNotEmpty ? Text(task.description) : null,
        trailing: IconButton(
          icon: Icon(Icons.delete_outline),
          onPressed: () => viewModel.deleteTask(task.id!),
        ),
      ),
    );
  }

  void _showAddTaskDialog(BuildContext context, TaskViewModel viewModel) {
    final titleController = TextEditingController();
    final descController = TextEditingController();

    Get.dialog(
      AlertDialog(
        title: Text('Add Task'),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: titleController,
                decoration: InputDecoration(labelText: 'Task Title'),
              ),
              SizedBox(height: 16),
              TextField(
                controller: descController,
                decoration: InputDecoration(labelText: 'Description (optional)'),
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
              if (titleController.text.isNotEmpty) {
                viewModel.addTask(
                  TaskModel(
                    title: titleController.text,
                    description: descController.text,
                  ),
                );
                Get.back();
              }
            },
            child: Text('Add'),
          ),
        ],
      ),
    );
  }
}
