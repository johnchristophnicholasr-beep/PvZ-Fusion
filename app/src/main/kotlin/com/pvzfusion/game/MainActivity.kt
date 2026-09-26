package com.pvzfusion.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import com.pvzfusion.game.ui.GameActivity
import com.pvzfusion.game.data.GameStateManager

class MainActivity : AppCompatActivity() {
    private lateinit var gameStateManager: GameStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStateManager = GameStateManager(this)

        val continueBtn = findViewById<Button>(R.id.btn_continue)
        val playBtn = findViewById<Button>(R.id.btn_play)
        val tutorialBtn = findViewById<Button>(R.id.btn_tutorial)
        val settingsBtn = findViewById<Button>(R.id.btn_settings)

        continueBtn.setOnClickListener {
            if (gameStateManager.hasSavedGame()) {
                startGame()
            }
        }

        playBtn.setOnClickListener {
            startGame()
        }

        tutorialBtn.setOnClickListener {
            // Start tutorial
            startActivity(Intent(this, GameActivity::class.java).apply {
                putExtra("mode", "tutorial")
            })
        }

        settingsBtn.setOnClickListener {
            // Open settings
        }
    }

    private fun startGame() {
        startActivity(Intent(this, GameActivity::class.java))
    }
}
