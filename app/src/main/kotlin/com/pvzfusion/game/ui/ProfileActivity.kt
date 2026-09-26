package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.GameStateManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        manager = GameStateManager(this)
        val state = manager.loadGameState(0)
        if (state != null) {
            findViewById<TextView>(R.id.player_name).text = "Gardener: ${state.playerName}"
            findViewById<TextView>(R.id.progress).text = "World ${state.currentWorld + 1} • Level ${state.currentLevel + 1}"
            findViewById<TextView>(R.id.stats).text = """Coins: ${state.coins}
Plants Unlocked: ${state.unlockedPlants.size}
Fusions Discovered: ${state.discoveredFusions.size}
Challenges Completed: ${state.completedChallenges.size}
Achievements: ${state.achievements.size}
Completion: ${state.completionPercent}%"""
        }
    }
}
