#!/bin/bash

# PvZ Fusion Build Configuration
# Sets up build environment and verifies all dependencies

echo "PvZ Fusion Build Configuration"
echo "==============================="
echo ""

echo "Checking requirements..."
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "ERROR: Java not found. Install JDK 1.8+"
    exit 1
fi
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=").*?(?=")' | head -1)
echo "✓ Java: $JAVA_VERSION"

# Check Gradle
if [ ! -f "gradlew" ]; then
    echo "ERROR: gradlew not found. Run from project root."
    exit 1
fi
echo "✓ Gradle wrapper: Found"

# Check Android SDK
if [ -z "$ANDROID_SDK_ROOT" ] && [ -z "$ANDROID_HOME" ]; then
    echo "WARNING: ANDROID_SDK_ROOT or ANDROID_HOME not set"
    echo "         Android Studio usually sets this automatically"
else
    echo "✓ Android SDK: Configured"
fi

# Verify build.gradle files
if [ ! -f "build.gradle.kts" ] || [ ! -f "app/build.gradle.kts" ]; then
    echo "ERROR: build.gradle.kts files not found"
    exit 1
fi
echo "✓ Gradle files: Valid"

# Check manifest
if [ ! -f "app/src/main/AndroidManifest.xml" ]; then
    echo "ERROR: AndroidManifest.xml not found"
    exit 1
fi
echo "✓ Android Manifest: Valid"

echo ""
echo "Ready to build!"
echo ""
echo "Next steps:"
echo "  1. Build debug APK:   bash build-debug.sh"
echo "  2. Build release APK: bash build-apk.sh"
echo "  3. Install to device: bash install-apk.sh"
echo ""
