package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.ChallengeDatabase
import com.pvzfusion.game.data.GameStateManager

class ChallengeActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        manager = GameStateManager(this)
        val state = manager.loadGameState(0)
        val list = findViewById<LinearLayout>(R.id.challenge_list)
        ChallengeDatabase.getChallenges().forEach { challenge ->
            val completed = state?.completedChallenges?.contains(challenge.id) ?: false
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 12, 16, 12)
                setBackgroundColor(if (completed) 0xFF8BC34A.toInt() else 0xFFFF9800.toInt())
            }
            row.addView(TextView(this).apply {
                text = "${if (completed) "✓" else "⚔"} ${challenge.name} [${challenge.tier}]"
                textSize = 18f
                setTextColor(0xFFFFFFFF.toInt())
            })
            row.addView(TextView(this).apply {
                text = "Required: ${challenge.requiredPlants.joinToString(", ")}\nDifficulty: ${challenge.difficulty}/10\nReward: ${challenge.rewards.entries.joinToString(", ") { (k, v) -> "$v $k" }}"
                textSize = 12f
                setTextColor(0xFFFFFFFF.toInt())
            })
            list.addView(row)
        }
    }
}
