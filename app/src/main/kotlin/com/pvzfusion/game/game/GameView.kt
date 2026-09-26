package com.pvzfusion.game.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.pvzfusion.game.data.*
import kotlin.math.sqrt

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var board = GameBoard()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var selected = "peashooter"
    private var running = true
    private var lastTime = System.currentTimeMillis()
    private val plants = PlantDatabase.getBasePlants()
    private val gameThread: Thread

    init {
        gameThread = Thread {
            while (running) {
                val now = System.currentTimeMillis()
                val delta = (now - lastTime).coerceAtMost(33L)
                lastTime = now
                GameEngine.update(board, delta)
                postInvalidate()
                Thread.sleep(16)
            }
        }
        gameThread.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()

        // Draw grass background
        canvas.drawColor(Color.rgb(126, 198, 91))

        // Draw sky
        paint.color = Color.rgb(220, 245, 255)
        canvas.drawRect(0f, 0f, w, h * 0.16f, paint)

        // Draw HUD
        paint.color = Color.DKGRAY
        paint.textSize = 34f
        canvas.drawText("Wave ${board.wave}    Sun ${board.sun}", 20f, 55f, paint)
        paint.textSize = 20f
        canvas.drawText("Tap a tile to place • Selected: $selected", 20f, 95f, paint)

        // Draw grid
        val tileW = w / board.width
        val tileH = (h * 0.8f) / board.height
        for (row in 0 until board.height) {
            for (col in 0 until board.width) {
                val l = col * tileW
                val t = h * 0.16f + row * tileH
                paint.color = if ((row + col) % 2 == 0) Color.rgb(112, 181, 72) else Color.rgb(101, 169, 66)
                canvas.drawRect(l + 2, t + 2, l + tileW - 2, t + tileH - 2, paint)
            }
        }

        // Draw plants with health bars
        board.plants.forEach { p ->
            paint.color = when (p.data.id) {
                "peashooter" -> Color.rgb(100, 180, 50)
                "ice_peashooter" -> Color.rgb(150, 200, 255)
                "cherry_bomb" -> Color.RED
                "wall_nut" -> Color.rgb(139, 69, 19)
                "repeater" -> Color.rgb(75, 150, 50)
                else -> Color.rgb(200, 100, 50)
            }
            val x = p.col * tileW + tileW / 2
            val y = h * 0.16f + p.row * tileH + tileH / 2
            canvas.drawCircle(x, y, 24f, paint)
            
            // Draw health bar
            val healthPercent = (p.health / p.data.health).coerceIn(0f, 1f)
            paint.color = Color.RED
            canvas.drawRect(x - 20, y + 30, x + 20, y + 35, paint)
            paint.color = Color.GREEN
            canvas.drawRect(x - 20, y + 30, x - 20 + 40 * healthPercent, y + 35, paint)
            
            paint.color = Color.WHITE
            paint.textSize = 12f
            canvas.drawText(p.data.name.take(3), x - 12f, y + 4f, paint)
        }

        // Draw zombies with health bars
        board.zombies.forEach { z ->
            paint.color = when (z.data.id) {
                "cone_zombie" -> Color.rgb(200, 150, 100)
                else -> Color.rgb(100, 100, 100)
            }
            val y = h * 0.16f + z.row * tileH + tileH / 2
            canvas.drawCircle(z.x / 100f * tileW, y, 20f, paint)
            
            // Draw health bar
            val healthPercent = (z.health / z.data.health).coerceIn(0f, 1f)
            paint.color = Color.RED
            canvas.drawRect(z.x / 100f * tileW - 18, y + 25, z.x / 100f * tileW + 18, y + 30, paint)
            paint.color = Color.GREEN
            canvas.drawRect(z.x / 100f * tileW - 18, y + 25, z.x / 100f * tileW - 18 + 36 * healthPercent, y + 30, paint)
            
            paint.color = Color.WHITE
            paint.textSize = 10f
            canvas.drawText("Z", z.x / 100f * tileW - 4f, y + 3f, paint)
        }

        // Draw projectiles with glow
        board.projectiles.forEach { p ->
            paint.color = when (p.type) {
                "ice_pea" -> Color.CYAN
                "bomb", "explosive_pea" -> Color.RED
                else -> Color.YELLOW
            }
            canvas.drawCircle(p.x / 100f * tileW, h * 0.16f + p.targetRow * tileH + tileH / 2, 5f, paint)
        }

        // Draw plant selector palette
        paint.color = Color.rgb(200, 200, 200)
        canvas.drawRect(0f, h - 120f, w, h, paint)
        
        paint.color = Color.WHITE
        paint.textSize = 18f
        canvas.drawText("PEA (100s)", 20f, h - 20f, paint)
        canvas.drawText("CHERRY (150s)", 100f, h - 20f, paint)
        canvas.drawText("ICE (125s)", 220f, h - 20f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action != MotionEvent.ACTION_UP) return true
        val w = width.toFloat()
        val h = height.toFloat()
        val tileW = w / board.width
        val tileH = (h * 0.8f) / board.height

        // Plant selector
        if (event.y > h - 140) {
            selected = when {
                event.x < 95 -> "peashooter"
                event.x < 205 -> "cherry_bomb"
                else -> "ice_peashooter"
            }
            invalidate()
            return true
        }

        // Board click
        if (event.y > h * 0.16f && event.y < h * 0.96f) {
            val col = (event.x / tileW).toInt().coerceIn(0, board.width - 1)
            val row = ((event.y - h * 0.16f) / tileH).toInt().coerceIn(0, board.height - 1)
            val data = plants.find { it.id == selected } ?: return true
            if (GameEngine.addPlant(board, selected, row, col)) {
                board = board.copy(sun = board.sun - data.cost)
            }
            invalidate()
        }
        return true
    }

    fun pause() { running = false }
    fun resume() { running = true; lastTime = System.currentTimeMillis() }
}
