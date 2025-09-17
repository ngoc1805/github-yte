package com.example.yte.Game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

data class Tank(
    var x: Float,
    var y: Float,
    var targetX: Float = x,
    var targetY: Float = y,
    private var screenWidth: Float = 1080f,
    private var screenHeight: Float = 1920f
) {
    
    fun updateScreenSize(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height
    }
    
    private fun getTankWidth() = screenWidth * GameConstants.TANK_WIDTH_RATIO
    private fun getTankHeight() = screenHeight * GameConstants.TANK_HEIGHT_RATIO
    private fun getGroundHeight() = screenHeight * GameConstants.GROUND_HEIGHT_RATIO
    
    fun update() {
        // Smooth movement towards target position
        val deltaX = targetX - x
        val deltaY = targetY - y
        
        if (kotlin.math.abs(deltaX) > 1f) {
            x += deltaX * 0.1f
        } else {
            x = targetX
        }
        
        if (kotlin.math.abs(deltaY) > 1f) {
            y += deltaY * 0.1f
        } else {
            y = targetY
        }
        
        // Keep tank within screen bounds
        x = x.coerceIn(
            getTankWidth() / 2f,
            screenWidth - getTankWidth() / 2f
        )
        
        // Keep tank on ground level
        y = screenHeight - getGroundHeight() - getTankHeight() / 2f
    }
    
    fun moveTo(newX: Float, newY: Float) {
        targetX = newX.coerceIn(
            getTankWidth() / 2f,
            screenWidth - getTankWidth() / 2f
        )
        // Tank stays on ground
        targetY = screenHeight - getGroundHeight() - getTankHeight() / 2f
    }
    
    fun draw(drawScope: DrawScope) {
        val tankWidth = getTankWidth()
        val tankHeight = getTankHeight()
        
        // Draw tank body
        drawScope.drawRect(
            color = Color(GameConstants.TANK_COLOR),
            topLeft = Offset(
                x - tankWidth / 2f,
                y - tankHeight / 2f
            ),
            size = Size(tankWidth, tankHeight)
        )
        
        // Draw tank cannon
        drawScope.drawRect(
            color = Color(GameConstants.TANK_CANNON_COLOR),
            topLeft = Offset(
                x + tankWidth / 3f,
                y - 10f
            ),
            size = Size(tankWidth / 3f, 20f)
        )
        
        // Draw tank border
        drawScope.drawRect(
            color = Color.Black,
            topLeft = Offset(
                x - tankWidth / 2f,
                y - tankHeight / 2f
            ),
            size = Size(tankWidth, tankHeight),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
        )
    }
    
    fun shoot(): Projectile {
        val tankWidth = getTankWidth()
        // Create projectile from cannon position
        val cannonX = x + tankWidth / 3f + tankWidth / 6f
        val cannonY = y - 10f
        
        return Projectile(
            x = cannonX,
            y = cannonY,
            velocityX = GameConstants.PROJECTILE_SPEED.toFloat(),
            velocityY = -GameConstants.PROJECTILE_SPEED.toFloat()
        )
    }
    
    fun getBounds(): android.graphics.RectF {
        val tankWidth = getTankWidth()
        val tankHeight = getTankHeight()
        return android.graphics.RectF(
            x - tankWidth / 2f,
            y - tankHeight / 2f,
            x + tankWidth / 2f,
            y + tankHeight / 2f
        )
    }
}