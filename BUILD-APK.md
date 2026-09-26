# PvZ Fusion APK Builder Guide

## Quick Start

### Build Release APK
```bash
bash build-apk.sh
```

Output: `dist/PvZ-Fusion-release.apk`

### Build Debug APK
```bash
bash build-debug.sh
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Auto-Install to Device
```bash
bash install-apk.sh
```

Requirements:
- Android SDK Platform Tools (includes `adb`)
- Connected Android device with USB debugging enabled
- Device unlocked

## Manual Build (Android Studio)

1. **Open project** in Android Studio
2. **Select app** module
3. **Build menu** → "Build Bundle(s)/APK(s)" → "Build APK(s)"
4. APK appears in `app/build/outputs/apk/release/`

## Manual Build (Command Line)

```bash
# Debug build
./gradlew assembleDebug

# Release build (unsigned)
./gradlew assembleRelease

# Test build
./gradlew assembleAndroidTest
```

## Signing for Play Store

Create a keystore:
```bash
keytool -genkey -v -keystore pvz-fusion.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias pvz-fusion-key
```

Add to `app/build.gradle.kts`:
```gradle
android {
    signingConfigs {
        create("release") {
            storeFile = file("path/to/pvz-fusion.keystore")
            storePassword = "your-password"
            keyAlias = "pvz-fusion-key"
            keyPassword = "your-password"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

Then build:
```bash
./gradlew assembleRelease
```

## APK Verification

### Check APK contents
```bash
unzip -l dist/PvZ-Fusion-release.apk | head -20
```

### Verify on device
```bash
adb shell pm list packages | grep pvz
adb shell am start -n com.pvzfusion.game/.MainActivity
```

### Check version
```bash
aapt dump badging dist/PvZ-Fusion-release.apk | grep version
```

## Troubleshooting

### "Gradle daemon not running"
```bash
./gradlew --stop
./gradlew assembleRelease
```

### "Compilation failed"
```bash
./gradlew clean
./gradlew build --stacktrace
```

### "ADB not found"
Install Android SDK Platform Tools:
- macOS: `brew install android-platform-tools`
- Linux: `sudo apt install android-tools-adb`
- Windows: Download from Android Studio or official SDK

### "Device not found"
```bash
# List connected devices
adb devices

# Reconnect device
adb kill-server
adb start-server
adb devices
```

## Optimization Flags

### Reduce APK size
```bash
./gradlew assembleRelease -x test --parallel
```

### Enable ProGuard/R8 minification
Already enabled in release build via `app/build.gradle.kts`

### Split APK by architecture
```gradle
android {
    bundle {
        enableSplit = true
    }
}
```

## Distribution

- **Google Play Store**: Upload signed APK via Play Console
- **Direct distribution**: Share signed APK via link/QR code
- **F-Droid**: Submit to open-source Android app repository
- **GitHub Releases**: Upload APK to repository releases tab

## Performance Targets

- **APK Size**: < 15 MB (uncompressed)
- **Startup**: < 2 seconds (cold launch)
- **FPS**: 60 FPS target on mid-range devices (API 24+)
- **Memory**: < 100 MB runtime

---

**Happy building!**
