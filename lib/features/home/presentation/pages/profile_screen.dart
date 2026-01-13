import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/features/viewmodels/view_models.dart';
import 'package:progressly/services/local_storage_service.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final viewModel = Get.find<ProfileViewModel>();

    return FutureBuilder<void>(
      future: viewModel.loadProfile(),
      builder: (context, snapshot) {
        return Scaffold(
          body: ListView(
            padding: EdgeInsets.all(16),
            children: [
              // User Profile Card
              FutureBuilder<dynamic>(
                future: LocalStorageService.getUserProfile(),
                builder: (context, snapshot) {
                  if (!snapshot.hasData) {
                    return Center(child: CircularProgressIndicator());
                  }

                  final profile = snapshot.data;

                  return Card(
                    child: Padding(
                      padding: EdgeInsets.all(20),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          CircleAvatar(
                            radius: 40,
                            child: Text(
                              profile?.name[0].toUpperCase() ?? 'P',
                              style: Theme.of(context)
                                  .textTheme
                                  .headlineLarge,
                            ),
                          ),
                          SizedBox(height: 16),
                          Text(
                            profile?.name ?? 'User',
                            style: Theme.of(context).textTheme.headlineMedium,
                          ),
                          SizedBox(height: 8),
                          Text(
                            '${profile?.age} years old • ${profile?.gender}',
                            style: Theme.of(context).textTheme.bodyLarge,
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Weight: ${profile?.weight} kg',
                            style: Theme.of(context).textTheme.bodyMedium,
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
              SizedBox(height: 24),
              // Stats
              Text(
                'Statistics',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              SizedBox(height: 16),
              FutureBuilder<void>(
                future: Future.delayed(Duration(milliseconds: 100)),
                builder: (context, snapshot) {
                  return Column(
                    children: [
                      _buildStatCard(
                        context,
                        'Today',
                        viewModel,
                      ),
                    ],
                  );
                },
              ),
              SizedBox(height: 24),
              // Settings
              Text(
                'Settings',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              SizedBox(height: 16),
              _buildSettingsTile(
                context,
                'Notifications',
                'Manage notification preferences',
                Icons.notifications_outlined,
              ),
              _buildSettingsTile(
                context,
                'Privacy',
                'Control your privacy settings',
                Icons.lock_outlined,
              ),
              _buildSettingsTile(
                context,
                'About',
                'About Progressly',
                Icons.info_outlined,
              ),
              SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                child: OutlinedButton.icon(
                  onPressed: () async {
                    await LocalStorageService.clearAll();
                    Get.offAllNamed('/onboarding');
                  },
                  icon: Icon(Icons.logout),
                  label: Text('Reset & Restart Onboarding'),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildStatCard(
    BuildContext context,
    String period,
    ProfileViewModel viewModel,
  ) {
    return FutureBuilder<dynamic>(
      future: viewModel.getDailySummary(DateTime.now()),
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return Card(
            child: Padding(
              padding: EdgeInsets.all(16),
              child: Center(child: CircularProgressIndicator()),
            ),
          );
        }

        final summary = snapshot.data;

        return Card(
          child: Padding(
            padding: EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  period,
                  style: Theme.of(context).textTheme.titleLarge,
                ),
                SizedBox(height: 16),
                GridView.count(
                  crossAxisCount: 2,
                  shrinkWrap: true,
                  physics: NeverScrollableScrollPhysics(),
                  crossAxisSpacing: 16,
                  mainAxisSpacing: 16,
                  children: [
                    _buildStatItem(
                      context,
                      'Tasks',
                      summary.tasksCompleted.toString(),
                      '✅',
                    ),
                    _buildStatItem(
                      context,
                      'Meals',
                      summary.mealsLogged.toString(),
                      '🍽️',
                    ),
                    _buildStatItem(
                      context,
                      'Water',
                      '${summary.waterIntake} ml',
                      '💧',
                    ),
                    _buildStatItem(
                      context,
                      'Habits',
                      summary.habitsCompleted.toString(),
                      '✨',
                    ),
                  ],
                ),
                SizedBox(height: 16),
                Container(
                  padding: EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Theme.of(context).primaryColor.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Total Calories',
                        style: Theme.of(context).textTheme.bodyLarge,
                      ),
                      Text(
                        '${summary.totalCalories} cal',
                        style: Theme.of(context)
                            .textTheme
                            .bodyLarge
                            ?.copyWith(
                              fontWeight: FontWeight.bold,
                            ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  Widget _buildStatItem(
    BuildContext context,
    String label,
    String value,
    String emoji,
  ) {
    return Container(
      padding: EdgeInsets.all(12),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey[700]!),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(emoji, style: TextStyle(fontSize: 28)),
          SizedBox(height: 8),
          Text(
            value,
            style: Theme.of(context)
                .textTheme
                .titleLarge
                ?.copyWith(fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 4),
          Text(
            label,
            style: Theme.of(context).textTheme.bodySmall,
          ),
        ],
      ),
    );
  }

  Widget _buildSettingsTile(
    BuildContext context,
    String title,
    String subtitle,
    IconData icon,
  ) {
    return Card(
      margin: EdgeInsets.only(bottom: 8),
      child: ListTile(
        leading: Icon(icon),
        title: Text(title),
        subtitle: Text(subtitle),
        trailing: Icon(Icons.arrow_forward_ios, size: 16),
        onTap: () {},
      ),
    );
  }
}
