package com.example.yte.Game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

data class Projectile(
    var x: Float,
    var y: Float,
    var velocityX: Float,
    var velocityY: Float,
    var isActive: Boolean = true,
    private var screenWidth: Float = 1080f,
    private var screenHeight: Float = 1920f
) {
    
    fun updateScreenSize(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height
    }
    
    private fun getGroundHeight() = screenHeight * GameConstants.GROUND_HEIGHT_RATIO
    
    fun update() {
        if (!isActive) return
        
        // Update position
        x += velocityX
        y += velocityY
        
        // Apply gravity
        velocityY += GameConstants.GRAVITY
        
        // Check if projectile is out of bounds
        if (x < 0 || x > screenWidth || 
            y > screenHeight - getGroundHeight()) {
            isActive = false
        }
    }
    
    fun draw(drawScope: DrawScope) {
        if (!isActive) return
        
        drawScope.drawCircle(
            color = Color(GameConstants.PROJECTILE_COLOR),
            radius = GameConstants.PROJECTILE_RADIUS,
            center = Offset(x, y)
        )
    }
    
    fun getBounds(): android.graphics.RectF {
        return android.graphics.RectF(
            x - GameConstants.PROJECTILE_RADIUS,
            y - GameConstants.PROJECTILE_RADIUS,
            x + GameConstants.PROJECTILE_RADIUS,
            y + GameConstants.PROJECTILE_RADIUS
        )
    }
}