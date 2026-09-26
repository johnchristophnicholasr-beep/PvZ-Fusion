package com.pvzfusion.game.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var isRunning = true
    private val paint = Paint().apply {
        color = Color.BLACK
        textSize = 48f
    }

    private val gameThread = GameThread(this)

    init {
        gameThread.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw sky
        canvas.drawColor(0xFF87CEEB.toInt())
        
        // Draw grass
        canvas.drawRect(0f, height * 0.6f, width.toFloat(), height.toFloat(), Paint().apply {
            color = 0xFF2E7D32.toInt()
        })
        
        // Draw game state
        canvas.drawText("PvZ Fusion", 50f, 100f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    fun pause() {
        isRunning = false
        gameThread.join()
    }

    fun resume() {
        isRunning = true
    }

    inner class GameThread(private val view: GameView) : Thread() {
        override fun run() {
            while (isRunning) {
                view.postInvalidate()
                Thread.sleep(16) // ~60 FPS
            }
        }
    }
}
