import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:timezone/timezone.dart' as tz;

class NotificationService {
  static final NotificationService _instance = NotificationService._internal();
  late FlutterLocalNotificationsPlugin _notificationsPlugin;

  factory NotificationService() {
    return _instance;
  }

  NotificationService._internal();

  Future<void> initialize() async {
    _notificationsPlugin = FlutterLocalNotificationsPlugin();

    const androidSetting = AndroidInitializationSettings('@mipmap/ic_launcher');
    const iosSetting = DarwinInitializationSettings(
      requestSoundPermission: true,
      requestBadgePermission: true,
      requestAlertPermission: true,
    );

    const initSettings = InitializationSettings(
      android: androidSetting,
      iOS: iosSetting,
    );

    await _notificationsPlugin.initialize(initSettings);

    // Request permissions
    await _notificationsPlugin
        .resolvePlatformSpecificImplementation<AndroidFlutterLocalNotificationsPlugin>()
        ?.requestNotificationsPermission();

    await _notificationsPlugin
        .resolvePlatformSpecificImplementation<IOSFlutterLocalNotificationsPlugin>()
        ?.requestPermissions(alert: true, badge: true, sound: true);
  }

  Future<void> showNotification({
    required int id,
    required String title,
    required String body,
    String? payload,
  }) async {
    const androidDetails = AndroidNotificationDetails(
      'progressly_channel',
      'Progressly Notifications',
      channelDescription: 'Notifications for Progressly app',
      importance: Importance.max,
      priority: Priority.high,
    );

    const iosDetails = DarwinNotificationDetails(
      presentAlert: true,
      presentBadge: true,
      presentSound: true,
    );

    const notificationDetails = NotificationDetails(
      android: androidDetails,
      iOS: iosDetails,
    );

    await _notificationsPlugin.show(
      id,
      title,
      body,
      notificationDetails,
      payload: payload,
    );
  }

  Future<void> scheduleNotification({
    required int id,
    required String title,
    required String body,
    required DateTime scheduledTime,
    String? payload,
  }) async {
    const androidDetails = AndroidNotificationDetails(
      'progressly_channel',
      'Progressly Notifications',
      channelDescription: 'Notifications for Progressly app',
      importance: Importance.max,
      priority: Priority.high,
    );

    const iosDetails = DarwinNotificationDetails(
      presentAlert: true,
      presentBadge: true,
      presentSound: true,
    );

    const notificationDetails = NotificationDetails(
      android: androidDetails,
      iOS: iosDetails,
    );

    await _notificationsPlugin.zonedSchedule(
      id,
      title,
      body,
      tz.TZDateTime.from(scheduledTime, tz.local),
      notificationDetails,
      androidScheduleMode: AndroidScheduleMode.exactAndAllowWhileIdle,
      uiLocalNotificationDateInterpretation:
          UILocalNotificationDateInterpretation.absoluteTime,
      payload: payload,
    );
  }

  Future<void> cancelNotification(int id) async {
    await _notificationsPlugin.cancel(id);
  }

  Future<void> cancelAllNotifications() async {
    await _notificationsPlugin.cancelAll();
  }

  // Convenience methods for common reminders
  Future<void> notifyHydrationReminder(int hour, int minute) async {
    await scheduleNotification(
      id: 1,
      title: '💧 Time to hydrate!',
      body: 'Remember to drink water and stay hydrated!',
      scheduledTime: _getNextScheduleTime(hour, minute),
      payload: 'water_reminder',
    );
  }

  Future<void> notifyMealLogging(int hour, int minute) async {
    await scheduleNotification(
      id: 2,
      title: '🍽️ Log your meal',
      body: 'Remember to log your meal for accurate tracking!',
      scheduledTime: _getNextScheduleTime(hour, minute),
      payload: 'meal_reminder',
    );
  }

  Future<void> notifyHabitCheck(int hour, int minute) async {
    await scheduleNotification(
      id: 3,
      title: '✨ Check your habits',
      body: 'Time to complete your daily habits!',
      scheduledTime: _getNextScheduleTime(hour, minute),
      payload: 'habit_reminder',
    );
  }

  Future<void> notifyTaskReminder(int hour, int minute) async {
    await scheduleNotification(
      id: 4,
      title: '✅ Review your tasks',
      body: 'Don\'t forget to complete your tasks!',
      scheduledTime: _getNextScheduleTime(hour, minute),
      payload: 'task_reminder',
    );
  }

  DateTime _getNextScheduleTime(int hour, int minute) {
    var scheduledTime = DateTime.now().add(const Duration(days: 1));
    scheduledTime = scheduledTime.copyWith(hour: hour, minute: minute, second: 0);

    if (DateTime.now().isBefore(scheduledTime)) {
      return scheduledTime;
    } else {
      return scheduledTime;
    }
  }
}
