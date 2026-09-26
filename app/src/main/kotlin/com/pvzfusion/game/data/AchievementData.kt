package com.pvzfusion.game.data

object AchievementDatabase {
    fun getAllAchievements(): List<Achievement> {
        return listOf(
            Achievement("first_plant", "Budding Gardener", "Place your first plant", "common"),
            Achievement("first_kill", "Zombie Slayer", "Defeat your first zombie", "common"),
            Achievement("collect_100_sun", "Sun Collector", "Collect 100 sun total", "uncommon"),
            Achievement("complete_world_1", "Garden Guardian", "Complete World 1", "uncommon"),
            Achievement("discover_fusion", "Fusion Artist", "Discover your first fusion", "rare"),
            Achievement("complete_all_challenges", "Master Gardener", "Complete all challenges", "legendary"),
            Achievement("survive_endless_10", "Wave Survivor", "Survive 10 waves in Endless mode", "epic"),
            Achievement("perfect_level", "Flawless", "Complete a level without taking damage", "epic")
        )
    }
}

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val rarity: String
)
