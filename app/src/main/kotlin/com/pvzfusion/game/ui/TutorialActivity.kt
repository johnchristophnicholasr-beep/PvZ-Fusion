package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        val list = findViewById<LinearLayout>(R.id.tutorial_list)
        val lessons = listOf(
            "Plant Placement" to "Tap any garden tile to place plants. They attack zombies automatically and need sun to deploy.",
            "Sun Collection" to "Sunflowers generate sun over time. Collect fallen sun by tapping it. More sun = more plants!",
            "Zombie Waves" to "Zombies spawn from the right and move left toward your house. Defeat all zombies in each wave to advance.",
            "Plant Abilities" to "Each plant has unique abilities. Peashooter shoots peas, Wall-Nut blocks, Cherry Bomb explodes. Mix and match!",
            "Plant Upgrades" to "Replay levels to earn coins. Use coins in the shop to upgrade your plants' damage, health, and cooldown.",
            "Plant Combinations" to "Compatible plants can fuse together! Combine 2× Cherry Bomb + 1× Peashooter to create Explosive Peashooter.",
            "Fusion Recipes" to "Fusion Encyclopedia tracks all recipes you've discovered. Many are hidden until you complete specific challenges.",
            "Unlocking Plants" to "Complete levels and challenges to unlock new plants. Higher worlds introduce stronger plants and strategies.",
            "Challenge Progression" to "Challenges are special battles with restrictions. Beat them to unlock powerful fusion plants and rewards.",
            "Save & Offline" to "Your progress saves automatically after every action. All gameplay is 100% offline—no internet needed."
        )
        lessons.forEach { (title, desc) ->
            val titleView = TextView(this).apply {
                text = title
                textSize = 20f
            }
            val descView = TextView(this).apply {
                text = desc
                textSize = 14f
                setPadding(16, 0, 16, 16)
            }
            list.addView(titleView)
            list.addView(descView)
        }
    }
}
