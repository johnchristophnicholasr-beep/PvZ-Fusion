package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.GameStateManager
import com.pvzfusion.game.data.AchievementDatabase

class AchievementsActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        manager = GameStateManager(this)
        val list = findViewById<LinearLayout>(R.id.achievement_list)
        val state = manager.loadGameState(0)
        val allAchievements = AchievementDatabase.getAllAchievements()
        allAchievements.forEach { achievement ->
            val unlocked = state?.achievements?.contains(achievement.id) ?: false
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 12, 16, 12)
                setBackgroundColor(if (unlocked) 0xFF4CAF50.toInt() else 0xFF999999.toInt())
            }
            row.addView(TextView(this).apply {
                text = "${if (unlocked) "✓" else "○"} ${achievement.name} [${achievement.rarity}]"
                textSize = 18f
                setTextColor(0xFFFFFFFF.toInt())
            })
            row.addView(TextView(this).apply {
                text = achievement.description
                textSize = 14f
                setTextColor(0xFFFFFFFF.toInt())
            })
            list.addView(row)
        }
    }
}
