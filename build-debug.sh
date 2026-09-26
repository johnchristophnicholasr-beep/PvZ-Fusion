#!/bin/bash

# PvZ Fusion Debug APK Builder
# Builds a debug APK for development/testing

set -e

echo "==================================="
echo "  PvZ Fusion Debug APK Builder"
echo "==================================="

if [ ! -f "build.gradle.kts" ]; then
    echo "ERROR: build.gradle.kts not found. Run from project root."
    exit 1
fi

echo ""
echo "[1/3] Cleaning previous builds..."
./gradlew clean

echo ""
echo "[2/3] Building debug APK..."
./gradlew assembleDebug

echo ""
echo "[3/3] Locating APK..."
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "ERROR: APK not found at $APK_PATH"
    exit 1
fi

echo ""
echo "==================================="
echo "  DEBUG BUILD COMPLETE!"
echo "==================================="
echo ""
echo "APK Location: $APK_PATH"
echo "File Size: $(du -h $APK_PATH | cut -f1)"
echo ""
echo "To install on device:"
echo "  adb install -r $APK_PATH"
