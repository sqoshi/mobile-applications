package com.app.lastmultiplayergame.game.components


import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
//TODO: mason stretch bricks in width, half od paddle at start, multiplayer score
class Ball(val cordX: Float, val cordY: Float, val radius: Float, val screenCords: ScreenCords) {
    val color = Paint().also { it.setARGB(123, 234, 11, 66) }
    var speedX = 10f
    var speedY = 10f
    var x = cordX
    var y = cordY

    fun draw(canvas: Canvas) {
        canvas.drawOval(RectF(x, y, x + radius, y + radius), color)
    }

    fun update(paddle: Paddle, wall: List<List<Brick>>) {
        bounceFromWalls()
        bounceFromPaddle(paddle)
        bounceFromBrickWall(wall)

        x += speedX //+ r1//if (kotlin.math.abs(speedX + r1) <= 10f) (speedX + r1) else 10f
        y += speedY //+ r2
        Log.d("BALL", "$x $y")
    }

    private fun spawnInMiddle() {
//        sleep(3000)
        x = (screenCords.right / 2).toFloat()
        y = (screenCords.bottom / 2).toFloat()
        speedX = 10f
        speedY = 10f
    }

    private fun bounceFromWalls() {
        if (x + radius >= screenCords.right)
            speedX = -speedX
        if (x + radius <= screenCords.left)
            speedX = -speedX
        if (y + radius >= screenCords.bottom) {
            // speedY = -speedY
            //respawn on mid
            spawnInMiddle()
        }

        if (y + radius <= screenCords.top)
            speedY = -speedY
    }

    private fun bounceFromPaddle(paddle: Paddle) {
        val lt = paddle.getLeftTopCords()
        val rb = paddle.getRightBottomCords()
        if (x >= lt.x && x <= rb.x && y + radius >= lt.y && y + radius <= rb.y) {
            speedX *= listOf(-1, 1).random()
            speedY = -speedY
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
        if (x >= brick.left && x <= brick.right && y + radius >= brick.top && y + radius <= brick.bottom) {
            speedX *= listOf(-1, 1).random()
            speedY = -speedY
            brick.hit()
        }

    }

}