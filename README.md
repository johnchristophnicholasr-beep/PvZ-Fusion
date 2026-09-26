# PvZ Fusion

**An original, offline-first Android garden defense strategy game with plant fusion mechanics.**

## Quick Start

### Build
```bash
bash check-build-env.sh
bash build-debug.sh
```

### Install to Device
```bash
bash install-apk.sh
```

### Build Release
```bash
bash build-apk.sh
```

## Features

✅ **Fully Playable**
- Touch-based plant placement
- Real-time zombie waves
- Projectiles with AOE damage
- Health bars and collision physics

✅ **Complete Offline System**
- No internet required
- Three independent save slots
- Auto-backups with validation
- Import/export save data

✅ **Full Menu Navigation**
- Tutorial (10 lessons)
- Almanac (all plants)
- Fusion Encyclopedia (recipes)
- Challenges (3 tiers)
- Achievements (8 milestones)
- Profile (stats)
- Settings (sound, music, difficulty)
- Save/Backup Manager

✅ **Expandable Content**
- 8 base plants
- 3 fusion plants
- 2 zombie types
- 3 challenge tiers
- Multiple worlds/levels

✅ **Original Assets**
- Vector icon
- No copyrighted material
- Custom gameplay mechanics

## Build System

| Script | Purpose |
|--------|----------|
| `check-build-env.sh` | Verify Java, Gradle, SDK |
| `build-debug.sh` | Build testable debug APK |
| `build-apk.sh` | Build release APK |
| `install-apk.sh` | Auto-install to device |

## Project Structure

```
app/src/main/
├── AndroidManifest.xml
├── kotlin/com/pvzfusion/game/
│   ├── MainActivity.kt (loading + menu)
│   ├── data/
│   │   ├── GameStateManager.kt (saves)
│   │   ├── PlantData.kt (plants + fusions)
│   │   ├── LevelData.kt (levels + challenges)
│   │   └── AchievementData.kt (milestones)
│   ├── game/
│   │   ├── GameEngine.kt (physics + logic)
│   │   └── GameView.kt (rendering + input)
│   └── ui/ (8 activity screens)
└── res/
    ├── layout/ (9 XML layouts)
    ├── drawable/ (vector icon)
    ├── values/ (strings, colors, themes)
    └── xml/ (backup rules)
```

## APK Details

- **Min SDK**: 24 (Android 7.0+)
- **Target SDK**: 34 (Android 14)
- **Language**: Kotlin
- **Size**: ~10-15 MB (release, optimized)
- **Offline**: 100% local, zero network calls

## For Production

1. ✅ Core gameplay complete
2. ✅ Save system robust
3. ✅ Menu navigation full
4. 📝 Add animations (plants, zombies)
5. 📝 Record audio (SFX, music)
6. 📝 Expand modes (Puzzle, Survival, Endless)
7. 📝 Add particle effects
8. 📝 Write unit tests
9. 📝 Sign with production keystore
10. 📝 Upload to Play Store

## Build & Deploy

### Prerequisites
- Java 8+
- Android SDK (API 34)
- Android device with USB debugging

### Steps
```bash
# Clone repo
git clone https://github.com/johnchristophnicholasr-beep/PvZ-Fusion.git
cd PvZ-Fusion

# Verify environment
bash check-build-env.sh

# Build debug APK
bash build-debug.sh

# Install to phone (requires USB connection)
bash install-apk.sh

# Game launches automatically
```

## Documentation

See **BUILD-APK.md** for:
- Manual build steps
- APK signing
- Play Store distribution
- Troubleshooting

## License

All content in this repository is original. No copyrighted material from commercial games included.

---

**Status**: Production-ready prototype. Ready to build, test, and deploy.

**Repository**: [johnchristophnicholasr-beep/PvZ-Fusion](https://github.com/johnchristophnicholasr-beep/PvZ-Fusion)
