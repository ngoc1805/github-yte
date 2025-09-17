package com.example.yte.Game

import androidx.compose.ui.unit.dp

object GameConstants {
    // Game physics
    const val GRAVITY = 0.5f
    const val GROUND_HEIGHT_RATIO = 0.2f // 20% of screen height
    
    // Tank properties (relative to screen)
    const val TANK_WIDTH_RATIO = 0.08f
    const val TANK_HEIGHT_RATIO = 0.05f
    const val TANK_SPEED = 8
    
    // Projectile properties
    const val PROJECTILE_RADIUS = 10f
    const val PROJECTILE_SPEED = 15
    
    // Cloud brick properties
    const val CLOUD_BRICK_WIDTH_RATIO = 0.06f
    const val CLOUD_BRICK_HEIGHT_RATIO = 0.03f
    const val CLOUD_BRICK_SPEED = 3
    const val CLOUD_BRICK_COUNT = 5
    const val CLOUD_BRICK_Y_RATIO = 0.2f // 20% from top
    
    // Colors
    const val BACKGROUND_COLOR = 0xFF87CEEB // Sky blue
    const val GROUND_COLOR = 0xFF8B4513 // Brown
    const val CLOUD_BRICK_COLOR = 0xFFFF6B6B // Red
    const val PROJECTILE_COLOR = 0xFFFFD700 // Gold
    const val TANK_COLOR = 0xFF2E8B57 // Sea green
    const val TANK_CANNON_COLOR = 0xFF228B22 // Forest green
}