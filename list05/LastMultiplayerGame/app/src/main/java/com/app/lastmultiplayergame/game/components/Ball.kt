package com.app.lastmultiplayergame.game.components


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.app.lastmultiplayergame.game.data.ScreenCords
import kotlin.math.pow
import kotlin.math.sqrt

class Ball(val cordX: Float, val cordY: Float, val radius: Float, val screenCords: ScreenCords) {
    val color = Paint().also { it.setARGB(123, 234, 11, 66) }

    private val accSpeed = 20f
    var speedX = accSpeed
    var speedY = accSpeed
    var x = cordX
    var y = cordY

    init {
        color.color = Color.WHITE
        color.setShadowLayer(4f, 0f, 0f, color.color);
    }

    fun draw(canvas: Canvas) {
        canvas.drawOval(RectF(x, y, x + radius, y + radius), color)
    }

    fun update(paddle: Paddle, wall: List<List<Brick>>) {
        bounceFromWalls()
        bounceFromPaddle(paddle)
        bounceFromBrickWall(wall)

        x += speedX
        y += speedY
    }

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

    private fun bounceFromWalls() {
        if (x + radius >= screenCords.right)
            speedX = -speedX
        if (x <= screenCords.left)
            speedX = -speedX
        if (y + radius >= screenCords.bottom) {
            spawnInMiddle()
        }

        if (y + radius <= screenCords.top)
            speedY = -speedY
    }

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

    private fun bounceFromBrickWall(wall: List<List<Brick>>) {
        for (row in wall) {
            for (brick in row) {
                if (brick.alive)
                    bounceFromBrick(brick)
            }
        }
    }

    private fun bounceFromBrick(brick: Brick) {
        if (x >= brick.left && x <= brick.right && y >= brick.top && y <= brick.bottom) {
            speedX *= getRandomSense()
            speedY = -speedY
            brick.hit()
        }

    }

    private fun getRandomSense(): Float {
        return listOf(-1f, 1f).random()

    }

}