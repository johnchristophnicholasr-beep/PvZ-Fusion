-keep class com.pvzfusion.game.** { *; }
-keep class com.pvzfusion.game.data.** { *; }
-keep class com.pvzfusion.game.game.** { *; }
-keep class com.pvzfusion.game.ui.** { *; }
-keepclassmembers class com.pvzfusion.game.** {
    <init>(...);
    *** get*(...);
    void set*(...);
}
-keep class com.google.gson.** { *; }
-keepclassmembers class ** {
    @com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn com.google.gson.**
-dontwarn android.**
-dontwarn androidx.**
