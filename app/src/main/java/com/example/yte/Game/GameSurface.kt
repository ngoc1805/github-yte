package com.example.yte.Game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSurface(navController: NavController) {
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tank Game") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        GameCanvas(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun GameCanvas(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    
    // Game state
    var tank by remember { mutableStateOf<Tank?>(null) }
    var projectiles by remember { mutableStateOf(listOf<Projectile>()) }
    var cloudBricks by remember { mutableStateOf(listOf<CloudBrick>()) }
    var screenWidth by remember { mutableFloatStateOf(0f) }
    var screenHeight by remember { mutableFloatStateOf(0f) }
    
    // Game loop
    LaunchedEffect(screenWidth, screenHeight) {
        if (screenWidth > 0 && screenHeight > 0) {
            // Initialize game objects with proper screen dimensions
            tank = Tank(
                x = screenWidth / 2f,
                y = screenHeight - (screenHeight * GameConstants.GROUND_HEIGHT_RATIO) - 
                    (screenHeight * GameConstants.TANK_HEIGHT_RATIO) / 2f,
                screenWidth = screenWidth,
                screenHeight = screenHeight
            )
            cloudBricks = initializeCloudBricks(screenWidth, screenHeight)
        }
    }
    
    LaunchedEffect(tank) {
        while (tank != null) {
            delay(16) // ~60 FPS
            
            // Update tank
            tank?.update()
            
            // Update projectiles
            projectiles = projectiles.map { projectile ->
                projectile.updateScreenSize(screenWidth, screenHeight)
                projectile.update()
                projectile
            }.filter { it.isActive }
            
            // Update cloud bricks
            cloudBricks.forEach { brick ->
                brick.updateScreenSize(screenWidth, screenHeight)
                brick.update()
            }
            
            // Check collisions between projectiles and cloud bricks
            projectiles.forEach { projectile ->
                cloudBricks.forEach { brick ->
                    if (projectile.isActive && brick.isActive && 
                        isColliding(projectile.getBounds(), brick.getBounds())) {
                        projectile.isActive = false
                        // Cloud bricks continue flying as requested
                    }
                }
            }
        }
    }
    
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Tap to shoot
                    tank?.let { currentTank ->
                        val newProjectile = currentTank.shoot()
                        newProjectile.updateScreenSize(screenWidth, screenHeight)
                        projectiles = projectiles + newProjectile
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    // Move tank to touch position
                    tank?.moveTo(change.position.x, change.position.y)
                }
            }
    ) {
        // Update screen dimensions
        screenWidth = size.width
        screenHeight = size.height
        
        // Draw background (sky with gradient effect)
        drawRect(
            color = Color(GameConstants.BACKGROUND_COLOR),
            size = Size(size.width, size.height)
        )
        
        // Draw simple clouds in background
        drawCloudBackground(this, size.width, size.height)
        
        // Draw ground
        val groundHeight = size.height * GameConstants.GROUND_HEIGHT_RATIO
        drawRect(
            color = Color(GameConstants.GROUND_COLOR),
            topLeft = Offset(0f, size.height - groundHeight),
            size = Size(size.width, groundHeight)
        )
        
        // Draw ground texture (simple lines)
        for (i in 0..5) {
            drawRect(
                color = Color(0xFF654321), // Darker brown
                topLeft = Offset(0f, size.height - groundHeight + (i * 10f)),
                size = Size(size.width, 2f)
            )
        }
        
        // Draw cloud bricks
        cloudBricks.forEach { brick ->
            brick.draw(this)
        }
        
        // Draw projectiles
        projectiles.forEach { projectile ->
            projectile.draw(this)
        }
        
        // Draw tank
        tank?.draw(this)
    }
}

private fun initializeCloudBricks(screenWidth: Float, screenHeight: Float): List<CloudBrick> {
    val bricks = mutableListOf<CloudBrick>()
    val brickWidth = screenWidth * GameConstants.CLOUD_BRICK_WIDTH_RATIO
    for (i in 0 until GameConstants.CLOUD_BRICK_COUNT) {
        bricks.add(
            CloudBrick(
                x = -brickWidth + (i * 200f), // Spread them out
                y = screenHeight * GameConstants.CLOUD_BRICK_Y_RATIO + (i % 3) * 80f, // Vary heights slightly
                screenWidth = screenWidth,
                screenHeight = screenHeight
            )
        )
    }
    return bricks
}

private fun isColliding(rect1: android.graphics.RectF, rect2: android.graphics.RectF): Boolean {
    return rect1.intersect(rect2)
}

private fun drawCloudBackground(drawScope: DrawScope, screenWidth: Float, screenHeight: Float) {
    // Draw simple background clouds for atmosphere
    val cloudColor = Color(0xFFFFFFFF).copy(alpha = 0.6f)
    
    // Cloud 1
    drawScope.drawCircle(
        color = cloudColor,
        radius = 40f,
        center = Offset(screenWidth * 0.2f, screenHeight * 0.15f)
    )
    drawScope.drawCircle(
        color = cloudColor,
        radius = 35f,
        center = Offset(screenWidth * 0.25f, screenHeight * 0.15f)
    )
    drawScope.drawCircle(
        color = cloudColor,
        radius = 30f,
        center = Offset(screenWidth * 0.3f, screenHeight * 0.15f)
    )
    
    // Cloud 2
    drawScope.drawCircle(
        color = cloudColor,
        radius = 45f,
        center = Offset(screenWidth * 0.7f, screenHeight * 0.25f)
    )
    drawScope.drawCircle(
        color = cloudColor,
        radius = 40f,
        center = Offset(screenWidth * 0.75f, screenHeight * 0.25f)
    )
    
    // Cloud 3
    drawScope.drawCircle(
        color = cloudColor,
        radius = 35f,
        center = Offset(screenWidth * 0.1f, screenHeight * 0.35f)
    )
    drawScope.drawCircle(
        color = cloudColor,
        radius = 32f,
        center = Offset(screenWidth * 0.14f, screenHeight * 0.35f)
    )
}