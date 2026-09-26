package com.pvzfusion.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.game.GameView

class GameActivity : AppCompatActivity() {
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameView = findViewById(R.id.game_view)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.resume()
    }
}
