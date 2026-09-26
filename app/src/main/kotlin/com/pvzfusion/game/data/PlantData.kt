package com.pvzfusion.game.data

data class Plant(
    val id: String,
    val name: String,
    val rarity: String, // COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    val cost: Int,
    val cooldown: Float,
    val health: Float,
    val damage: Float,
    val range: Float,
    val attackSpeed: Float,
    val abilities: List<String>,
    val fusionCompatible: List<String>,
    val isFusion: Boolean = false,
    val recipe: FusionRecipe? = null
)

data class FusionRecipe(
    val id: String,
    val name: String,
    val ingredients: Map<String, Int>, // plantId -> quantity
    val resultPlantId: String,
    val unlockMethod: String, // "automatic", "challenge", "discovery"
    val challengeId: String? = null
)

data class Zombie(
    val id: String,
    val name: String,
    val health: Float,
    val speed: Float,
    val damage: Float,
    val armor: Float,
    val abilities: List<String>
)

object PlantDatabase {
    fun getBasePlants(): List<Plant> {
        return listOf(
            // Basic Plants
            Plant(
                id = "peashooter",
                name = "Peashooter",
                rarity = "COMMON",
                cost = 100,
                cooldown = 1.5f,
                health = 100f,
                damage = 20f,
                range = 5f,
                attackSpeed = 1f,
                abilities = listOf("shoot_pea"),
                fusionCompatible = listOf("cherry_bomb", "ice_peashooter")
            ),
            Plant(
                id = "sunflower",
                name = "Sunflower",
                rarity = "COMMON",
                cost = 50,
                cooldown = 0f,
                health = 80f,
                damage = 0f,
                range = 0f,
                attackSpeed = 0f,
                abilities = listOf("generate_sun"),
                fusionCompatible = listOf()
            ),
            Plant(
                id = "cherry_bomb",
                name = "Cherry Bomb",
                rarity = "RARE",
                cost = 150,
                cooldown = 4f,
                health = 120f,
                damage = 200f,
                range = 2.5f,
                attackSpeed = 0.25f,
                abilities = listOf("explosive_attack"),
                fusionCompatible = listOf("peashooter", "squash")
            ),
            Plant(
                id = "ice_peashooter",
                name = "Ice Peashooter",
                rarity = "UNCOMMON",
                cost = 125,
                cooldown = 2f,
                health = 100f,
                damage = 15f,
                range = 5f,
                attackSpeed = 1f,
                abilities = listOf("shoot_ice_pea", "slow_enemy"),
                fusionCompatible = listOf("peashooter", "cherry_bomb")
            ),
            Plant(
                id = "squash",
                name = "Squash",
                rarity = "RARE",
                cost = 125,
                cooldown = 3f,
                health = 110f,
                damage = 150f,
                range = 1f,
                attackSpeed = 0.33f,
                abilities = listOf("jump_squash"),
                fusionCompatible = listOf("cherry_bomb")
            )
        )
    }

    fun getFusions(): List<Plant> {
        return listOf(
            Plant(
                id = "cherry_bomb_peashooter_fusion",
                name = "Explosive Peashooter",
                rarity = "EPIC",
                cost = 200,
                cooldown = 2.5f,
                health = 140f,
                damage = 80f,
                range = 4.5f,
                attackSpeed = 1f,
                abilities = listOf("fire_explosive_pea", "aoe_explosion"),
                fusionCompatible = listOf(),
                isFusion = true,
                recipe = FusionRecipe(
                    id = "cherry_bomb_peashooter_fusion",
                    name = "Explosive Peashooter",
                    ingredients = mapOf("cherry_bomb" to 2, "peashooter" to 1),
                    resultPlantId = "cherry_bomb_peashooter_fusion",
                    unlockMethod = "challenge",
                    challengeId = "fusion_challenge_1"
                )
            )
        )
    }
}
