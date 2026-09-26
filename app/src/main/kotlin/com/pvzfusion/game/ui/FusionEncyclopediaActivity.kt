package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.PlantDatabase
import com.pvzfusion.game.data.GameStateManager

class FusionEncyclopediaActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fusion_encyclopedia)
        manager = GameStateManager(this)
        val list = findViewById<LinearLayout>(R.id.fusion_list)
        val state = manager.loadGameState(0)
        val fusions = PlantDatabase.getFusions()
        fusions.forEach { fusion ->
            val discovered = state?.discoveredFusions?.contains(fusion.id) ?: false
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 12, 16, 12)
            }
            row.addView(TextView(this).apply {
                text = if (discovered) "${fusion.name} (${fusion.rarity})" else "??? (Locked)"
                textSize = 18f
            })
            if (discovered && fusion.recipe != null) {
                row.addView(TextView(this).apply {
                    val ingredients = fusion.recipe.ingredients.entries.joinToString(" + ") { (plant, count) -> "$count× $plant" }
                    text = "Recipe: $ingredients"
                    textSize = 14f
                })
                row.addView(TextView(this).apply {
                    text = "Cost: ${fusion.cost}  |  Damage: ${fusion.damage.toInt()}  |  AOE: ${fusion.recipe.unlockMethod}"
                    textSize = 12f
                })
            } else if (!discovered) {
                row.addView(TextView(this).apply {
                    text = "Complete challenges and discover recipes to unlock."
                    textSize = 12f
                })
            }
            list.addView(row)
        }
    }
}
