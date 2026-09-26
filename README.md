# PvZ Fusion - Android Garden Strategy Game

**A fully original, offline-first Android garden defense strategy game with plant fusion mechanics, multiple game modes, and complete save management.**

## Features

✅ **Complete Offline Gameplay** - No internet required. All content plays locally.

✅ **Original Assets** - Vector-drawn app icon, custom plants, zombies, and environments. No copyrighted material.

✅ **Plant Fusion System** - Combine compatible plants into powerful new units. 2× Cherry Bomb + 1× Peashooter = Explosive Peashooter.

✅ **Three Save Slots** - Independent local save slots with separate progression for each player.

✅ **Automatic Backups** - Five automatic backups per slot with validated recovery.

✅ **Full Touch Controls** - Plant placement, resource management, and strategy gameplay.

✅ **Multiple Game Modes** - Story, Challenges, Tutorial, Puzzle, Survival, Endless (framework ready).

✅ **Progression System** - Multiple worlds, levels, plant unlocks, challenges, and achievements.

✅ **Expansion-Ready** - Fully editable JSON-like data files for plants, zombies, levels, fusions, and challenges.

## Game Mechanics

- **Plants**: Base defense towers with unique abilities, cooldowns, and damage types.
- **Zombies**: Enemy waves with varying health, speed, and armor.
- **Projectiles**: Plant attacks with collision detection and AOE damage.
- **Fusion Recipes**: Combine plants to create stronger units with custom stats.
- **Challenges**: Special battles with restrictions that unlock fusion plants.
- **Achievements**: Progress milestones with unlock rewards.

## Build & Run

1. Open the project in **Android Studio** (Android Gradle Plugin 8.1+)
2. Select the **app** run configuration
3. Build to Android API 34 target
4. Deploy to device or emulator (Android 7.0+, API 24+)

## Project Structure

```
app/src/main/
├── AndroidManifest.xml       # App activities and permissions
├── kotlin/com/pvzfusion/game/
│   ├── MainActivity.kt       # Launcher with loading screen
│   ├── data/
│   │   ├── GameStateManager.kt      # Save slots, backups, import/export
│   │   ├── PlantData.kt             # Plants and fusion recipes
│   │   ├── LevelData.kt             # Levels, worlds, challenges
│   │   └── AchievementData.kt       # Achievements
│   ├── game/
│   │   ├── GameEngine.kt    # Physics, collisions, spawning
│   │   └── GameView.kt      # Rendering and touch input
│   └── ui/
│       ├── GameActivity.kt  # Gameplay screen
│       ├── AlmanacActivity.kt
│       ├── FusionEncyclopediaActivity.kt
│       ├── ChallengeActivity.kt
│       ├── AchievementsActivity.kt
│       ├── ProfileActivity.kt
│       ├── SaveBackupActivity.kt
│       └── TutorialActivity.kt
└── res/
    ├── layout/          # XML UI layouts
    ├── drawable/        # Vector graphics and icons
    ├── values/          # Strings, colors, themes
    └── xml/             # Backup and extraction rules
```

## Editable Game Data

All gameplay content is stored in Kotlin data classes for easy modification:

- **Plants**: `PlantDatabase.getBasePlants()` - Name, cost, damage, cooldown, abilities
- **Fusions**: `PlantDatabase.getFusions()` - Recipes, ingredients, unlock conditions
- **Levels**: `LevelDatabase.getLevelsForWorld()` - Worlds, environments, zombie waves
- **Challenges**: `ChallengeDatabase.getChallenges()` - Tiers, rewards, restrictions
- **Achievements**: `AchievementDatabase.getAllAchievements()` - Milestones and descriptions

Edit any Kotlin file, rebuild, and redeploy to test changes instantly.

## Save System

- **Active Save**: `app/files/pvz_fusion/save_slots/slot_X.json`
- **Automatic Backups**: `app/files/pvz_fusion/backups/auto_X_*.json` (newest 5 per slot)
- **Manual Backups**: `app/files/pvz_fusion/backups/manual_X_*.json` (exported by player)
- **Validation**: All backups are validated before restoration. Corrupted saves never delete current progress.

## Offline Architecture

- ✅ All resources bundled in APK
- ✅ Zero network calls during gameplay
- ✅ SharedPreferences + local JSON files
- ✅ Atomic writes prevent data corruption
- ✅ Auto-backup on every save

## License

Original game design and all artwork/code in this repository is created from scratch. No copyrighted material from commercial PvZ versions is included.

## Next Steps for Production

- Add original animated sprites for plants and zombies
- Record/compose original sound effects and music
- Implement additional game modes (Puzzle, Survival, Endless)
- Add visual feedback (particle effects, damage numbers, animations)
- Expand level content and challenge variety
- Optimize rendering for lower-end Android devices
- Add automated unit and UI tests
- Prepare release APK signing

---

**Status**: Playable offline prototype with core mechanics, data foundations, and save management ready for feature expansion.
