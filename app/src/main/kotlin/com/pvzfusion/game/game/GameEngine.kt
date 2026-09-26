package com.pvzfusion.game.game

import com.pvzfusion.game.data.*
import kotlin.math.sqrt
import kotlin.random.Random

data class PlacedPlant(val id: String, val row: Int, val col: Int, var health: Float, var cooldown: Float = 0f, val data: Plant)
data class Projectile(var x: Float, val y: Float, val targetRow: Int, val damage: Float, val speed: Float = 3f, val type: String, val aoeRadius: Float = 0f, val createdAt: Long = System.currentTimeMillis())
data class Zombie(val id: String, val row: Int, var x: Float, val data: com.pvzfusion.game.data.Zombie, var health: Float, var speed: Float)
data class GameBoard(val width: Int = 8, val height: Int = 5, var sun: Int = 150, val wave: Int = 1, val gameOver: Boolean = false, val won: Boolean = false, val plants: MutableList<PlacedPlant> = mutableListOf(), val projectiles: MutableList<Projectile> = mutableListOf(), val zombies: MutableList<Zombie> = mutableListOf(), val waveZombiesSpawned: Int = 0, val waveZombiesRemaining: Int = 5)

object GameEngine {
    fun update(board: GameBoard, deltaMs: Long) {
        val deltaSec = deltaMs / 1000f
        board.plants.forEach { it.cooldown = (it.cooldown - deltaSec).coerceAtLeast(0f) }
        board.projectiles.forEach { p -> moveProjectile(board, p, deltaSec) }
        board.zombies.forEach { z -> z.x -= z.speed * deltaSec }
        spawnZombies(board, deltaMs)
        board.plants.forEach { plant -> attemptShoot(board, plant, deltaSec) }
        checkCollisions(board)
        board.zombies.removeAll { it.health <= 0 }
        board.projectiles.removeAll { p -> p.x < -100 }
    }

    fun addPlant(board: GameBoard, plantId: String, row: Int, col: Int): Boolean {
        val data = PlantDatabase.getBasePlants().find { it.id == plantId } ?: PlantDatabase.getFusions().find { it.id == plantId } ?: return false
        if (board.sun < data.cost) return false
        if (board.plants.any { it.row == row && it.col == col }) return false
        board.plants.add(PlacedPlant(plantId, row, col, data.health, 0f, data))
        return true
    }

    private fun moveProjectile(board: GameBoard, p: Projectile, deltaSec: Float) {
        p.x += p.speed * 100 * deltaSec
        if (p.x > board.width * 100) return
        
        val hit = mutableSetOf<Zombie>()
        board.zombies.filter { it.row == p.targetRow && it.x in (p.x - 30)..(p.x + 30) }.forEach { z ->
            z.health -= p.damage
            hit.add(z)
        }
        
        if (p.aoeRadius > 0 && hit.isNotEmpty()) {
            board.zombies.filter { other -> other.row == p.targetRow && !hit.contains(other) && sqrt((other.x - p.x) * (other.x - p.x)) <= p.aoeRadius }.forEach { it.health -= (p.damage * 0.6f) }
        }
    }

    private fun attemptShoot(board: GameBoard, plant: PlacedPlant, deltaSec: Float) {
        if (plant.cooldown > 0) return
        val data = plant.data
        val target = board.zombies.filter { it.row == plant.row && it.x > plant.col * 100 }.minByOrNull { it.x } ?: return
        plant.cooldown = data.cooldown
        
        when (data.id) {
            "peashooter" -> board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 150f, "pea"))
            "ice_peashooter" -> board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 130f, "ice_pea"))
            "cherry_bomb" -> {
                board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 120f, "bomb", 80f))
                plant.health -= 100f
            }
            "cherry_bomb_peashooter_fusion" -> {
                board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 140f, "explosive_pea", 60f))
            }
            "repeater" -> {
                board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 150f, "pea"))
                board.projectiles.add(Projectile(plant.col.toFloat() * 100, plant.row.toFloat() * 100, plant.row, data.damage, 150f, "pea"))
            }
        }
    }

    private fun spawnZombies(board: GameBoard, timeMs: Long) {
        if (board.waveZombiesSpawned < board.waveZombiesRemaining && Random.nextFloat() < 0.02f) {
            val row = Random.nextInt(0, board.height)
            val zombieData = listOf(
                com.pvzfusion.game.data.Zombie("basic_zombie", "Basic", 100f, 25f, 10f, 0f, listOf()),
                com.pvzfusion.game.data.Zombie("cone_zombie", "Cone", 150f, 20f, 10f, 20f, listOf())
            ).random()
            board.zombies.add(Zombie("z_${board.zombies.size}", row, board.width.toFloat() * 100, zombieData, zombieData.health, zombieData.speed))
        }
    }

    private fun checkCollisions(board: GameBoard) {
        board.plants.removeAll { p -> p.health <= 0 }
        board.zombies.forEach { z ->
            val collision = board.plants.find { it.row == z.row && it.col.toFloat() * 100 >= z.x - 50 }
            if (collision != null) {
                z.health -= 5f
                collision.health -= collision.data.damage * 0.05f
            }
        }
    }
}
