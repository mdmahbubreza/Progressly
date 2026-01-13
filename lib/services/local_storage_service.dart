import 'package:shared_preferences/shared_preferences.dart';
import 'package:progressly/data/models/models.dart';
import 'dart:convert';

class LocalStorageService {
  static late SharedPreferences _prefs;
  static const String _userProfileKey = 'user_profile';
  static const String _onboardingCompleteKey = 'onboarding_complete';
  static const String _notificationsEnabledKey = 'notifications_enabled';
  static const String _dailyWaterGoalKey = 'daily_water_goal';

  static Future<void> initialize() async {
    _prefs = await SharedPreferences.getInstance();
  }

  // User Profile Management
  static Future<void> saveUserProfile(UserProfile profile) async {
    final jsonString = jsonEncode(profile.toJson());
    await _prefs.setString(_userProfileKey, jsonString);
  }

  static Future<UserProfile?> getUserProfile() async {
    final jsonString = _prefs.getString(_userProfileKey);
    if (jsonString != null) {
      return UserProfile.fromJson(jsonDecode(jsonString));
    }
    return null;
  }

  static Future<void> deleteUserProfile() async {
    await _prefs.remove(_userProfileKey);
  }

  // Onboarding Management
  static Future<void> completeOnboarding() async {
    await _prefs.setBool(_onboardingCompleteKey, true);
  }

  static Future<bool> isOnboardingComplete() async {
    return _prefs.getBool(_onboardingCompleteKey) ?? false;
  }

  static Future<void> resetOnboarding() async {
    await _prefs.remove(_onboardingCompleteKey);
  }

  // Notifications Management
  static Future<void> setNotificationsEnabled(bool enabled) async {
    await _prefs.setBool(_notificationsEnabledKey, enabled);
  }

  static Future<bool> areNotificationsEnabled() async {
    return _prefs.getBool(_notificationsEnabledKey) ?? true;
  }

  // Water Goal Management
  static Future<void> setDailyWaterGoal(int goalInMl) async {
    await _prefs.setInt(_dailyWaterGoalKey, goalInMl);
  }

  static int getDailyWaterGoal() {
    return _prefs.getInt(_dailyWaterGoalKey) ?? 2000;
  }

  static int calculateWaterGoal(int weight, String gender) {
    int baseGoal = weight * 35;
    if (gender.toLowerCase() == 'female') {
      baseGoal = (baseGoal * 0.9).toInt();
    } else if (gender.toLowerCase() == 'male') {
      baseGoal = (baseGoal * 1.1).toInt();
    }
    return baseGoal;
  }

  // Clear all data
  static Future<void> clearAll() async {
    await _prefs.clear();
  }
}
