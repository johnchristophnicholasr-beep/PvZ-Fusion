#!/bin/bash

# PvZ Fusion APK Builder Script
# Builds a release APK for Android distribution

set -e

echo "==================================="
echo "  PvZ Fusion APK Builder v1.0"
echo "==================================="

if [ ! -f "build.gradle.kts" ]; then
    echo "ERROR: build.gradle.kts not found. Run from project root."
    exit 1
fi

echo ""
echo "[1/4] Cleaning previous builds..."
./gradlew clean

echo ""
echo "[2/4] Building release APK..."
./gradlew assembleRelease

echo ""
echo "[3/4] Locating APK..."
APK_PATH="app/build/outputs/apk/release/app-release.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "ERROR: APK not found at $APK_PATH"
    exit 1
fi

echo ""
echo "[4/4] Creating distribution package..."
DIST_DIR="dist"
mkdir -p "$DIST_DIR"
cp "$APK_PATH" "$DIST_DIR/PvZ-Fusion-release.apk"

echo ""
echo "==================================="
echo "  BUILD COMPLETE!"
echo "==================================="
echo ""
echo "APK Location: $DIST_DIR/PvZ-Fusion-release.apk"
echo "File Size: $(du -h $DIST_DIR/PvZ-Fusion-release.apk | cut -f1)"
echo ""
echo "To install on device:"
echo "  adb install -r $DIST_DIR/PvZ-Fusion-release.apk"
echo ""
echo "To share for distribution:"
echo "  - Sign the APK with your keystore (recommended for Play Store)"
echo "  - Upload to Google Play Store or distribute directly"
echo ""
