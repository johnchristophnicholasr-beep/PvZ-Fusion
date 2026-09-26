package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.PlantDatabase

class AlmanacActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almanac)
        val list = findViewById<LinearLayout>(R.id.plant_list)
        val allPlants = PlantDatabase.getBasePlants() + PlantDatabase.getFusions()
        allPlants.forEach { plant ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 12, 16, 12)
            }
            row.addView(TextView(this).apply {
                text = "${plant.name} • ${plant.rarity}"
                textSize = 18f
            })
            row.addView(TextView(this).apply {
                text = "Cost: ${plant.cost}  |  Damage: ${plant.damage.toInt()}  |  Health: ${plant.health.toInt()}  |  Cooldown: ${plant.cooldown}s"
                textSize = 14f
            })
            row.addView(TextView(this).apply {
                text = "${plant.abilities.joinToString(", ")}"
                textSize = 12f
            })
            list.addView(row)
        }
    }
}
