package com.pvzfusion.game.data

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.util.Date

/** Offline, atomic save storage with isolated slots and validated backups. */
class GameStateManager(context: Context) {
    companion object {
        const val SLOT_COUNT = 3
        private const val MAX_AUTOMATIC_BACKUPS = 5
        private const val FORMAT_VERSION = 1
    }

    private val gson = Gson()
    private val root = File(context.filesDir, "pvz_fusion")
    private val backupDir = File(root, "backups")
    private val saveSlotsDir = File(root, "save_slots")

    init { backupDir.mkdirs(); saveSlotsDir.mkdirs() }

    fun createNewGame(slotId: Int, playerName: String) {
        requireValidSlot(slotId)
        saveGameState(slotId, GameState.new(slotId, playerName))
    }

    fun saveGameState(slotId: Int, state: GameState, createBackup: Boolean = true) {
        requireValidSlot(slotId)
        val normalized = state.copy(playerId = slotId, formatVersion = FORMAT_VERSION, lastSaved = Date())
        val json = gson.toJson(normalized)
        if (createBackup) createAutomaticBackup(slotId, normalized)
        val target = slotFile(slotId)
        val temp = File(target.parentFile, "${target.name}.tmp")
        temp.writeText(json)
        if (!temp.renameTo(target)) {
            target.writeText(json)
            temp.delete()
        }
    }

    fun loadGameState(slotId: Int): GameState? = readState(slotFile(slotId))?.takeIf { it.playerId == slotId }

    fun hasSavedGame(slotId: Int = 0): Boolean = loadGameState(slotId) != null

    fun allSlotSummaries(): List<SlotSummary> = (0 until SLOT_COUNT).map { id ->
        val state = loadGameState(id)
        SlotSummary(id, state?.playerName ?: "Empty Slot", state?.currentWorld ?: 0, state?.currentLevel ?: 0,
            state?.completionPercent ?: 0, state?.unlockedPlants?.size ?: 0, state?.discoveredFusions?.size ?: 0,
            state?.lastSaved?.time ?: 0L, state != null)
    }

    fun deleteSlot(slotId: Int) {
        requireValidSlot(slotId)
        slotFile(slotId).delete()
        automaticBackups(slotId).forEach(File::delete)
    }

    fun getBackups(slotId: Int): List<BackupInfo> {
        requireValidSlot(slotId)
        return (automaticBackups(slotId) + manualBackups(slotId))
            .sortedByDescending { it.lastModified() }
            .mapNotNull { file -> readState(file)?.let { BackupInfo(file.name, file.lastModified(), file.length(), file.name.startsWith("auto_"), it.formatVersion) } }
    }

    fun restoreBackup(slotId: Int, backupFileName: String): Boolean {
        requireValidSlot(slotId)
        val source = safeBackupFile(slotId, backupFileName) ?: return false
        val restored = readState(source) ?: return false
        val current = loadGameState(slotId)
        if (current != null) writeBackup(slotId, current, "safety")
        return try { saveGameState(slotId, restored, createBackup = false); true } catch (_: Exception) { false }
    }

    fun exportBackup(slotId: Int, exportPath: String): File? {
        val state = loadGameState(slotId) ?: return null
        val destination = File(exportPath, "pvz_fusion_slot_${slotId}_${System.currentTimeMillis()}.json")
        destination.parentFile?.mkdirs(); destination.writeText(gson.toJson(state)); return destination
    }

    /** Validates before writing and never touches the active save when invalid. */
    fun importBackup(importFile: File, slotId: Int): Boolean {
        requireValidSlot(slotId)
        val imported = readState(importFile) ?: return false
        if (imported.playerName.isBlank() || imported.playerId !in 0 until SLOT_COUNT) return false
        val current = loadGameState(slotId)
        if (current != null) writeBackup(slotId, current, "safety")
        saveGameState(slotId, imported.copy(playerId = slotId), createBackup = false)
        return true
    }

    private fun createAutomaticBackup(slotId: Int, state: GameState) {
        writeBackup(slotId, state, "auto")
        automaticBackups(slotId).sortedByDescending { it.lastModified() }.drop(MAX_AUTOMATIC_BACKUPS).forEach(File::delete)
    }

    private fun writeBackup(slotId: Int, state: GameState, kind: String) {
        File(backupDir, "${kind}_${slotId}_${System.currentTimeMillis()}.json").writeText(gson.toJson(state))
    }

    private fun readState(file: File): GameState? = try {
        if (!file.exists() || file.length() == 0L) null else gson.fromJson(file.readText(), GameState::class.java)
    } catch (_: Exception) { null }

    private fun safeBackupFile(slotId: Int, name: String): File? {
        if (name.contains('/') || name.contains('\\')) return null
        return (automaticBackups(slotId) + manualBackups(slotId)).firstOrNull { it.name == name }
    }
    private fun automaticBackups(slotId: Int) = backupDir.listFiles { f -> f.name.startsWith("auto_${slotId}_") }?.toList() ?: emptyList()
    private fun manualBackups(slotId: Int) = backupDir.listFiles { f -> f.name.startsWith("manual_${slotId}_") }?.toList() ?: emptyList()
    private fun slotFile(slotId: Int) = File(saveSlotsDir, "slot_$slotId.json")
    private fun requireValidSlot(slotId: Int) { require(slotId in 0 until SLOT_COUNT) { "Save slot must be 0, 1, or 2" } }
}

data class GameState(
    val playerId: Int,
    val playerName: String,
    var currentWorld: Int = 0,
    var currentLevel: Int = 0,
    var coins: Int = 0,
    val unlockedPlants: MutableSet<String> = mutableSetOf("sunflower", "peashooter"),
    val discoveredFusions: MutableSet<String> = mutableSetOf(),
    val completedChallenges: MutableSet<String> = mutableSetOf(),
    val plantUpgrades: MutableMap<String, Int> = mutableMapOf(),
    val achievements: MutableSet<String> = mutableSetOf(),
    var tutorialCompleted: Boolean = false,
    var lastSaved: Date = Date(),
    val formatVersion: Int = 1,
    val completedLevels: MutableSet<String> = mutableSetOf(),
    val unlockedModes: MutableSet<String> = mutableSetOf("story"),
    val highScores: MutableMap<String, Int> = mutableMapOf(),
    val survivalRecords: MutableMap<String, Int> = mutableMapOf()
) {
    val completionPercent: Int get() = ((completedLevels.size.coerceAtMost(30) / 30f) * 100).toInt()
    companion object { fun new(id: Int, name: String) = GameState(playerId = id, playerName = name.ifBlank { "Gardener" }) }
}

data class BackupInfo(val fileName: String, val timestamp: Long, val size: Long, val automatic: Boolean, val version: Int)
data class SlotSummary(val slotId: Int, val playerName: String, val world: Int, val level: Int, val completion: Int, val plants: Int, val fusions: Int, val lastSaved: Long, val exists: Boolean)
