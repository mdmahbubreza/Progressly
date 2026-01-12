-keep class com.progressly.app.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Room
-keep @androidx.room.Entity class * { *; }
-dontwarn androidx.room.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
