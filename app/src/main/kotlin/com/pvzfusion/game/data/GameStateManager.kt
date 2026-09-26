package com.pvzfusion.game.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Date

class GameStateManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("pvz_fusion_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val backupDir = File(context.filesDir, "backups")
    private val saveSlotsDir = File(context.filesDir, "save_slots")

    init {
        backupDir.mkdirs()
        saveSlotsDir.mkdirs()
    }

    // Save slot functions
    fun createNewGame(slotId: Int, playerName: String) {
        val gameState = GameState(
            playerId = slotId,
            playerName = playerName,
            currentWorld = 0,
            currentLevel = 0,
            coins = 0,
            unlockedPlants = mutableSetOf(),
            discoveredFusions = mutableSetOf(),
            completedChallenges = mutableSetOf(),
            plantUpgrades = mutableMapOf(),
            achievements = mutableSetOf(),
            tutorialCompleted = false,
            lastSaved = Date()
        )
        saveGameState(slotId, gameState)
    }

    fun saveGameState(slotId: Int, state: GameState) {
        val slotFile = File(saveSlotsDir, "slot_$slotId.json")
        val json = gson.toJson(state)
        slotFile.writeText(json)
        createBackup(slotId, state)
    }

    fun loadGameState(slotId: Int): GameState? {
        val slotFile = File(saveSlotsDir, "slot_$slotId.json")
        return if (slotFile.exists()) {
            try {
                val json = slotFile.readText()
                gson.fromJson(json, GameState::class.java)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun hasSavedGame(): Boolean {
        return File(saveSlotsDir, "slot_0.json").exists()
    }

    fun deleteSlot(slotId: Int) {
        val slotFile = File(saveSlotsDir, "slot_$slotId.json")
        slotFile.delete()
    }

    // Backup functions
    fun createBackup(slotId: Int, state: GameState) {
        val backupFile = File(backupDir, "slot_${slotId}_backup_${System.currentTimeMillis()}.json")
        val json = gson.toJson(state)
        backupFile.writeText(json)
        pruneOldBackups(slotId)
    }

    fun getBackups(slotId: Int): List<BackupInfo> {
        val backupFiles = backupDir.listFiles() { file ->
            file.name.startsWith("slot_${slotId}_backup_")
        } ?: emptyArray()

        return backupFiles
            .sortedByDescending { it.lastModified() }
            .take(5)
            .map { file ->
                BackupInfo(
                    fileName = file.name,
                    timestamp = file.lastModified(),
                    size = file.length()
                )
            }
    }

    fun restoreBackup(slotId: Int, backupFileName: String) {
        val backupFile = File(backupDir, backupFileName)
        if (backupFile.exists()) {
            val json = backupFile.readText()
            val state = gson.fromJson(json, GameState::class.java)
            saveGameState(slotId, state)
        }
    }

    private fun pruneOldBackups(slotId: Int) {
        val backupFiles = backupDir.listFiles() { file ->
            file.name.startsWith("slot_${slotId}_backup_")
        } ?: return

        if (backupFiles.size > 5) {
            backupFiles.sortedBy { it.lastModified() }.take(backupFiles.size - 5).forEach { it.delete() }
        }
    }

    // Export/Import
    fun exportBackup(slotId: Int, exportPath: String) {
        val state = loadGameState(slotId) ?: return
        val exportFile = File(exportPath, "pvz_fusion_backup_slot_${slotId}_${System.currentTimeMillis()}.json")
        val json = gson.toJson(state)
        exportFile.writeText(json)
    }

    fun importBackup(importFile: File, slotId: Int): Boolean {
        return try {
            val json = importFile.readText()
            val state = gson.fromJson(json, GameState::class.java)
            saveGameState(slotId, state)
            true
        } catch (e: Exception) {
            false
        }
    }
}

data class GameState(
    val playerId: Int,
    val playerName: String,
    var currentWorld: Int,
    var currentLevel: Int,
    var coins: Int,
    val unlockedPlants: MutableSet<String>,
    val discoveredFusions: MutableSet<String>,
    val completedChallenges: MutableSet<String>,
    val plantUpgrades: MutableMap<String, Int>,
    val achievements: MutableSet<String>,
    var tutorialCompleted: Boolean,
    var lastSaved: Date
)

data class BackupInfo(
    val fileName: String,
    val timestamp: Long,
    val size: Long
)
