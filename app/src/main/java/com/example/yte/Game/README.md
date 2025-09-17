# Tank Game Implementation

This is a simple 2D tank game implemented in Kotlin using Jetpack Compose for the YTE medical application.

## Game Description

The Tank Game is a simple arcade-style game where the player controls a tank and shoots at flying cloud bricks. The game features:

- **Tank Control**: Touch and drag to move the tank horizontally across the screen
- **Shooting**: Tap anywhere on the screen to shoot projectiles from the tank
- **Flying Obstacles**: Cloud bricks continuously fly from left to right across the sky
- **Physics**: Projectiles are affected by gravity and have realistic trajectories
- **Infinite Gameplay**: Cloud bricks respawn on the left when they exit the right side of the screen

## Technical Implementation

### Files Created:

1. **Constants.kt** - Game configuration constants with responsive design ratios
2. **Tank.kt** - Player-controlled tank with smooth movement and shooting mechanics
3. **Projectile.kt** - Bullet physics with gravity simulation and collision detection
4. **CloudBrick.kt** - Flying cloud bricks with infinite scrolling behavior
5. **GameSurface.kt** - Main game canvas with touch controls and 60 FPS game loop

### Features:

- **Responsive Design**: Game adapts to different screen sizes using ratio-based dimensions
- **Smooth Movement**: Tank moves smoothly to touch positions using interpolation
- **Physics Simulation**: Realistic projectile physics with gravity
- **Collision Detection**: Accurate collision detection between projectiles and cloud bricks
- **Touch Controls**: Intuitive touch and drag for movement, tap to shoot
- **Game Loop**: 60 FPS game loop for smooth gameplay
- **Clean Architecture**: Component-based design for easy maintenance and extension

### Integration:

- Added to MainActivity navigation with route "TankGame"
- Accessible from HomeScreen with a dedicated "Tank Game" button
- Uses custom tank icon created as XML drawable resource

## Navigation

To access the game:
1. Open the YTE app
2. Go to the Home screen
3. Tap the "Tank Game" button
4. Use touch controls to play

## Controls

- **Move Tank**: Touch and drag horizontally on the screen
- **Shoot**: Tap anywhere on the screen
- **Back**: Use the back button in the top bar to return to the main app

## Game Mechanics

- Tank stays on the ground level and can only move horizontally
- Projectiles follow realistic physics with gravity affecting their trajectory
- Cloud bricks fly continuously from left to right at different heights
- When projectiles hit cloud bricks, the projectile disappears but bricks continue flying
- Game runs at 60 FPS for smooth performance

## Future Enhancements

Potential improvements that could be added:
- Score system
- Different types of projectiles
- Power-ups
- Tank image resources (currently uses simple shapes)
- Sound effects
- Particle effects for explosions
- Multiple levels with different cloud brick patterns