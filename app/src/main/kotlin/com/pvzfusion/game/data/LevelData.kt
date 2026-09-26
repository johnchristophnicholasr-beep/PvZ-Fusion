package com.pvzfusion.game.data

object LevelDatabase {
    fun getLevelsForWorld(world: Int): List<Level> {
        return when (world) {
            0 -> listOf(
                Level("world_0_level_0", "Daytime - Day 1", 0, 0, "day", 5, listOf("basic_zombie"), 100, 1),
                Level("world_0_level_1", "Daytime - Day 2", 0, 1, "day", 5, listOf("basic_zombie", "cone_zombie"), 150, 1),
                Level("world_0_level_2", "Nighttime", 0, 2, "night", 5, listOf("basic_zombie"), 200, 2),
                Level("world_0_level_3", "Challenge 1", 0, 3, "day", 5, listOf("cone_zombie"), 250, 2),
                Level("world_0_level_4", "Boss: Wave Master", 0, 4, "day", 5, listOf("basic_zombie"), 300, 3)
            )
            1 -> listOf(
                Level("world_1_level_0", "Pool - Stage 1", 1, 0, "pool", 6, listOf("basic_zombie"), 150, 2),
                Level("world_1_level_1", "Pool - Stage 2", 1, 1, "pool", 6, listOf("cone_zombie"), 200, 2),
                Level("world_1_level_2", "Fog", 1, 2, "fog", 5, listOf("basic_zombie", "cone_zombie"), 250, 3)
            )
            else -> emptyList()
        }
    }

    fun getTutorialLevels(): List<Level> {
        return listOf(
            Level("tutorial_0", "Plant your first Peashooter", -1, 0, "day", 1, listOf(), 50, 0),
            Level("tutorial_1", "Collect sun from Sunflowers", -1, 1, "day", 1, listOf("basic_zombie"), 50, 0),
            Level("tutorial_2", "Build a balanced defense", -1, 2, "day", 2, listOf("basic_zombie", "cone_zombie"), 100, 1),
            Level("tutorial_3", "Fuse plants for power", -1, 3, "day", 2, listOf("basic_zombie"), 150, 1)
        )
    }
}

data class Level(
    val id: String,
    val name: String,
    val world: Int,
    val level: Int,
    val environment: String, // day, night, pool, fog, etc.
    val rows: Int,
    val zombieWaves: List<String>,
    val initialSun: Int,
    val waveCount: Int
)

data class Challenge(
    val id: String,
    val name: String,
    val tier: String, // BASIC, ADVANCED, ELITE, LEGENDARY, ULTIMATE
    val requiredPlants: List<String>,
    val restrictions: List<String>,
    val rewards: Map<String, Int>,
    val unlockedFusions: List<String>,
    val difficulty: Float
)

object ChallengeDatabase {
    fun getChallenges(): List<Challenge> {
        return listOf(
            Challenge(
                id = "fusion_challenge_1",
                name = "Explosive Mastery",
                tier = "ELITE",
                requiredPlants = listOf("cherry_bomb", "peashooter"),
                restrictions = listOf("max_sun_1000", "no_wall_nut"),
                rewards = mapOf("coins" to 500, "plants" to 1),
                unlockedFusions = listOf("cherry_bomb_peashooter_fusion"),
                difficulty = 7.5f
            ),
            Challenge(
                id = "fusion_challenge_2",
                name = "Frozen Garden",
                tier = "ADVANCED",
                requiredPlants = listOf("ice_peashooter", "cherry_bomb"),
                restrictions = listOf("fire_plants_disabled"),
                rewards = mapOf("coins" to 300),
                unlockedFusions = listOf("ice_cherry_fusion"),
                difficulty = 5f
            ),
            Challenge(
                id = "fusion_challenge_3",
                name = "Fortress Builder",
                tier = "LEGENDARY",
                requiredPlants = listOf("wall_nut", "repeater"),
                restrictions = listOf("rapid_spawns"),
                rewards = mapOf("coins" to 750, "plants" to 2),
                unlockedFusions = listOf("wall_repeater_fusion"),
                difficulty = 9f
            )
        )
    }
}
