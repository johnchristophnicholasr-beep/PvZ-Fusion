# PvZ Fusion

PvZ Fusion is an original, offline-first Android garden strategy prototype. It contains editable Kotlin data models for plants, zombies, levels, fusion recipes, progression, and local save data.

## Build

Open the project in Android Studio with Android Gradle Plugin 8.1+ and run the `app` configuration. The project targets Android API 34 and supports Android 7.0+ (API 24).

## Current prototype

- Animated staged loading screen with real local initialization gates.
- Original garden-house vector app icon and colorful menu.
- Touch-based five-row garden board with plant selection and resource spending.
- Base plants and the two-Cherry + Peashooter explosive fusion recipe in editable data.
- Three isolated local save slots.
- Atomic active-save writes, JSON validation, safety backups, and five-backup automatic retention per slot.
- Offline operation with no network dependency.

All gameplay content is original and should be expanded with original art, audio, zombie simulation, challenge screens, almanac pages, and automated instrumentation tests before production release.
