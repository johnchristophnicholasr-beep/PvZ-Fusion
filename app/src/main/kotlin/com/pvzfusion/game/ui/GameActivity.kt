package com.pvzfusion.game.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.GameStateManager
import com.pvzfusion.game.game.GameView

class GameActivity : AppCompatActivity() {
    private var gameView: GameView? = null
    private lateinit var manager: GameStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        manager = GameStateManager(this)
        gameView = GameView(this)
        findViewById<FrameLayout>(R.id.game_container).addView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameView?.pause()
    }
}
