#!/bin/bash

# PvZ Fusion APK Installer Script
# Automatically detects connected Android device and installs APK

set -e

echo "==================================="
echo "  PvZ Fusion APK Installer"
echo "==================================="

if ! command -v adb &> /dev/null; then
    echo "ERROR: adb not found. Install Android SDK Platform Tools."
    exit 1
fi

echo ""
echo "Checking connected devices..."
DEVICE_COUNT=$(adb devices | grep -c "device$")

if [ $DEVICE_COUNT -eq 0 ]; then
    echo "ERROR: No Android devices connected."
    echo "Please connect a device via USB and enable USB debugging."
    exit 1
fi

echo "Found $DEVICE_COUNT device(s)."

APK_PATH="dist/PvZ-Fusion-release.apk"
if [ ! -f "$APK_PATH" ]; then
    echo ""
    echo "APK not found at $APK_PATH"
    echo "Building release APK..."
    bash build-apk.sh
fi

echo ""
echo "Installing $APK_PATH..."
adb install -r "$APK_PATH"

echo ""
echo "==================================="
echo "  INSTALLATION COMPLETE!"
echo "==================================="
echo ""
echo "Launching PvZ Fusion..."
adb shell am start -n com.pvzfusion.game/.MainActivity
echo ""
echo "Enjoy!"
