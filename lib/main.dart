import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:progressly/config/theme/app_theme.dart';
import 'package:progressly/features/onboarding/presentation/pages/onboarding_page.dart';
import 'package:progressly/features/home/presentation/pages/home_page.dart';
import 'package:progressly/services/local_storage_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Initialize local storage
  await LocalStorageService.initialize();
  
  runApp(const ProgresslyApp());
}

class ProgresslyApp extends StatelessWidget {
  const ProgresslyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'Progressly',
      theme: AppTheme.lightTheme,
      darkTheme: AppTheme.darkTheme,
      themeMode: ThemeMode.dark,
      home: FutureBuilder<bool>(
        future: LocalStorageService.isOnboardingComplete(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Scaffold(
              body: Center(child: CircularProgressIndicator()),
            );
          }
          
          final isOnboardingComplete = snapshot.data ?? false;
          return isOnboardingComplete ? const HomePage() : const OnboardingPage();
        },
      ),
      getPages: [
        GetPage(name: '/home', page: () => const HomePage()),
        GetPage(name: '/onboarding', page: () => const OnboardingPage()),
      ],
    );
  }
}
