package com.app.lastmultiplayergame.game.components


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.app.lastmultiplayergame.game.data.ScreenCords
import com.app.lastmultiplayergame.game.math.AuxiliaryOperations
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Object represent ball in game.
 */
class Ball(
    cordX: Float,
    cordY: Float,
    private val radius: Float,
    private val screenCords: ScreenCords
) {
    val color = Paint().also { it.setARGB(123, 234, 11, 66) }

    private val accSpeed =
        AuxiliaryOperations.getHypotenuse(
            screenCords.bottom.toFloat(),
            screenCords.right.toFloat()
        ) / 125f
    var speedX = accSpeed
    var speedY = accSpeed
    var x = cordX
    var y = cordY

    init {
        color.color = Color.WHITE
        color.setShadowLayer(4f, 0f, 0f, color.color);
    }

    /**
     * Draws ball in position with radius in color.
     */
    fun draw(canvas: Canvas) {
        canvas.drawOval(RectF(x, y, x + radius, y + radius), color)
    }

    /**
     * Updates ball cords and bounces from other objects if needed.
     */
    fun update(paddle: Paddle, wall: List<List<Brick>>) {
        bounceFromWalls()
        bounceFromPaddle(paddle)
        bounceFromBrickWall(wall)

        x += speedX
        y += speedY
    }

    /**
     * Spawns ball in center of screen.
     */
    private fun spawnInMiddle() {
        x = (screenCords.right / 2).toFloat()
        y = (screenCords.bottom / 2).toFloat()
        val first = (5..accSpeed.toInt()).random().toFloat()
        val second = sqrt(accSpeed.toDouble().pow(2) - first.toDouble().pow(2))
        if (second.toInt() == 0) {
            spawnInMiddle()
        } else {
            speedX = first * getRandomSense()
            speedY = second.toFloat() * getRandomSense()
        }
    }

    /**
     * Bounces ball from wall. By changing speed vector.
     */
    private fun bounceFromWalls() {
        if (x + radius >= screenCords.right)
            speedX = -speedX
        if (x <= screenCords.left)
            speedX = -speedX
        if (y + radius >= screenCords.bottom)
            spawnInMiddle()
        if (y <= screenCords.top)
            speedY = -speedY
    }

    /**
     * Bounces from paddle ( a little bit randomly).
     */
    private fun bounceFromPaddle(paddle: Paddle) {
        val lt = paddle.getLeftTopCords()
        val rb = paddle.getRightBottomCords()
        if (x >= lt.x && x <= rb.x && y + radius >= lt.y && y + radius <= rb.y) {
            val first = (5..accSpeed.toInt()).random().toFloat()
            val second = sqrt(accSpeed.toDouble().pow(2) - first.toDouble().pow(2)).toFloat()
            val senseX = if (speedX < 0) -1f else 1f
            speedX = first * senseX
            speedY = -second
        }

    }

    /**
     * Handle bouncing for each brick.
     */
    private fun bounceFromBrickWall(wall: List<List<Brick>>) {
        for (row in wall) {
            for (brick in row) {
                if (brick.alive)
                    bounceFromBrick(brick)
            }
        }
    }

    /**
     * Bounce from brick and hits that ( decrements hp by 1).
     */
    private fun bounceFromBrick(brick: Brick) {
        if (x >= brick.left && x <= brick.right && y >= brick.top && y <= brick.bottom) {
            speedX *= getRandomSense()
            speedY = -speedY
            brick.hit()
        }

    }

    /**
     * Returns randomly -1 or 1.
     */
    private fun getRandomSense(): Float {
        return listOf(-1f, 1f).random()

    }

}