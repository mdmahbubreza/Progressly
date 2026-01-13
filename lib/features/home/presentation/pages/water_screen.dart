import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/config/theme/app_theme.dart';
import 'package:progressly/services/local_storage_service.dart';

class WaterScreen extends StatelessWidget {
  const WaterScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<WaterViewModel>();
    viewModel.dailyGoal.value = LocalStorageService.getDailyWaterGoal();

    return Scaffold(
      body: Obx(() {
        if (viewModel.isLoading.value) {
          return Center(child: CircularProgressIndicator());
        }

        return ListView(
          padding: EdgeInsets.all(16),
          children: [
            // Progress Card
            Card(
              child: Padding(
                padding: EdgeInsets.all(20),
                child: Column(
                  children: [
                    Text(
                      'Today\'s Hydration',
                      style: Theme.of(context).textTheme.titleLarge,
                    ),
                    SizedBox(height: 24),
                    Stack(
                      alignment: Alignment.center,
                      children: [
                        SizedBox(
                          width: 150,
                          height: 150,
                          child: CircularProgressIndicator(
                            value: viewModel.getProgressPercentage(),
                            strokeWidth: 8,
                            backgroundColor: Colors.grey[700],
                            valueColor: AlwaysStoppedAnimation<Color>(
                              AppTheme.primaryColor,
                            ),
                          ),
                        ),
                        Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Obx(
                              () => Text(
                                '${viewModel.todayTotalWater.value} ml',
                                style: Theme.of(context)
                                    .textTheme
                                    .headlineMedium
                                    ?.copyWith(
                                      color: AppTheme.primaryColor,
                                    ),
                              ),
                            ),
                            Text(
                              'of ${viewModel.dailyGoal.value} ml',
                              style: Theme.of(context).textTheme.bodySmall,
                            ),
                          ],
                        ),
                      ],
                    ),
                    SizedBox(height: 24),
                    Obx(
                      () => Text(
                        'Remaining: ${viewModel.getRemainingWater()} ml',
                        style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                          color: AppTheme.textSecondary,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(height: 24),
            // Quick Add Buttons
            Text(
              'Quick Add',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            SizedBox(height: 12),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildQuickButton(context, '250ml', 250, viewModel),
                _buildQuickButton(context, '500ml', 500, viewModel),
                _buildQuickButton(context, '750ml', 750, viewModel),
              ],
            ),
            SizedBox(height: 24),
            // Today's Logs
            Text(
              'Today\'s Logs',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            SizedBox(height: 12),
            if (viewModel.waterLogs.isEmpty)
              Center(
                child: Padding(
                  padding: EdgeInsets.symmetric(vertical: 32),
                  child: Text('No water logs yet'),
                ),
              )
            else
              ...viewModel.waterLogs.map(
                (log) => Card(
                  margin: EdgeInsets.only(bottom: 8),
                  child: ListTile(
                    title: Text('${log.amount} ml'),
                    subtitle: Text(log.loggedDate.toString().split(' ')[0]),
                    trailing: IconButton(
                      icon: Icon(Icons.delete_outline),
                      onPressed: () => viewModel.deleteWaterLog(log.id!),
                    ),
                  ),
                ),
              ),
          ],
        );
      }),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () => _showCustomWaterDialog(context, viewModel),
        label: Text('Custom'),
        icon: Icon(Icons.add),
      ),
    );
  }

  Widget _buildQuickButton(
    BuildContext context,
    String label,
    int amount,
    WaterViewModel viewModel,
  ) {
    return ElevatedButton(
      onPressed: () => viewModel.quickLog(amount),
      child: Text(label),
    );
  }

  void _showCustomWaterDialog(BuildContext context, WaterViewModel viewModel) {
    final amountController = TextEditingController();

    Get.dialog(
      AlertDialog(
        title: Text('Log Water'),
        content: TextField(
          controller: amountController,
          keyboardType: TextInputType.number,
          decoration: InputDecoration(
            labelText: 'Amount (ml)',
            hintText: 'e.g., 250',
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Get.back(),
            child: Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              if (amountController.text.isNotEmpty) {
                viewModel.logWater(int.parse(amountController.text));
                Get.back();
              }
            },
            child: Text('Log'),
          ),
        ],
      ),
    );
  }
}
