package com.example.yte.Game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

data class CloudBrick(
    var x: Float,
    var y: Float,
    var isActive: Boolean = true,
    private var screenWidth: Float = 1080f,
    private var screenHeight: Float = 1920f
) {
    
    fun updateScreenSize(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height
    }
    
    private fun getBrickWidth() = screenWidth * GameConstants.CLOUD_BRICK_WIDTH_RATIO
    private fun getBrickHeight() = screenHeight * GameConstants.CLOUD_BRICK_HEIGHT_RATIO
    
    fun update() {
        if (!isActive) return
        
        // Move from left to right
        x += GameConstants.CLOUD_BRICK_SPEED
        
        // Reset position when it goes off screen (infinite loop effect)
        if (x > screenWidth + getBrickWidth()) {
            x = -getBrickWidth()
        }
    }
    
    fun draw(drawScope: DrawScope) {
        if (!isActive) return
        
        val brickWidth = getBrickWidth()
        val brickHeight = getBrickHeight()
        
        // Draw a simple bright colored rectangle for cloud brick
        drawScope.drawRect(
            color = Color(GameConstants.CLOUD_BRICK_COLOR),
            topLeft = Offset(x, y),
            size = Size(brickWidth, brickHeight)
        )
        
        // Add a simple border to make it more visible
        drawScope.drawRect(
            color = Color.Black,
            topLeft = Offset(x, y),
            size = Size(brickWidth, brickHeight),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
        )
    }
    
    fun getBounds(): android.graphics.RectF {
        val brickWidth = getBrickWidth()
        val brickHeight = getBrickHeight()
        return android.graphics.RectF(
            x,
            y,
            x + brickWidth,
            y + brickHeight
        )
    }
}