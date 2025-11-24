package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // Call reset to initialize the ball at the center
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // Calculate new velocity using Equation 1: v1 = v0 + 0.5 * (a1 + a0) * deltaTime
        val newVelocityX = velocityX + 0.5f * (xAcc + accX) * dT
        val newVelocityY = velocityY + 0.5f * (yAcc + accY) * dT

        // Calculate distance traveled using Equation 2:
        // l = v0 * deltaTime + (1/6) * deltaTime^2 * (3*a0 + a1)
        val distanceX = velocityX * dT + (1.0f / 6.0f) * dT * dT * (3 * accX + xAcc)
        val distanceY = velocityY * dT + (1.0f / 6.0f) * dT * dT * (3 * accY + yAcc)

        // Update position
        posX += distanceX
        posY += distanceY

        // Update velocity
        velocityX = newVelocityX
        velocityY = newVelocityY

        // Store current acceleration for next iteration
        accX = xAcc
        accY = yAcc
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        val radius = ballSize / 2f

        // Check left boundary
        if (posX - radius < 0) {
            posX = radius
            velocityX = 0f
            accX = 0f
        }

        // Check right boundary
        if (posX + radius > backgroundWidth) {
            posX = backgroundWidth - radius
            velocityX = 0f
            accX = 0f
        }

        // Check top boundary
        if (posY - radius < 0) {
            posY = radius
            velocityY = 0f
            accY = 0f
        }

        // Check bottom boundary
        if (posY + radius > backgroundHeight) {
            posY = backgroundHeight - radius
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // Reset position to center of screen
        posX = backgroundWidth / 2f
        posY = backgroundHeight / 2f

        // Reset velocities to zero
        velocityX = 0f
        velocityY = 0f

        // Reset accelerations to zero
        accX = 0f
        accY = 0f

        // Reset the first update flag
        isFirstUpdate = true
    }
}