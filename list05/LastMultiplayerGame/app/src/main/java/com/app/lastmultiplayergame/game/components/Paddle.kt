package com.app.lastmultiplayergame.game.components

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log

class Paddle(
    val screenLeft: Int,
    val screenTop: Int,
    val screenRight: Int,
    val screenBottom: Int
) {
    val color = Paint().also { it.setARGB(255, 22, 254, 123) }

    // const
    val width = (screenRight - screenLeft) / 3.0f
    val height = (screenBottom - screenTop) / 28.0f
    val y = screenBottom - 2.5f * height  // y is const. not mutable
    val top = y - height / 2.0f
    val bottom = y + height / 2.0f

    var x = (screenRight - screenLeft) / 2.0f // center at begining
    var left = x - width / 2.0f
    var right = x + height / 2.0f

    var speed = 10.0f

    fun getCenterCords(): Point {
        return Point(x, y)
    }

    fun getLeftTopCords(): Point {
        return Point(left, top)

    }

    fun getRightBottomCords(): Point {
        return Point(right, bottom)

    }


    fun updateX(sense: Float) {
//        Log.d("UPDATE X", "UPDATE X $sense")
        if (right < screenRight && left > screenLeft) {
            val paddleHalfWidth = width / 2.0f
            var newX = x + sense * speed
            var newLeft = newX - paddleHalfWidth
            var newRight = newX + paddleHalfWidth
            if (newLeft <= screenLeft) {
                // handle left limit
                newLeft = screenLeft + 1.0f
                newRight = newLeft + width
                newX = newLeft + paddleHalfWidth
            } else if (newRight >= screenRight) {
                // handle right limit
                newRight = screenRight - 1.0f
                newLeft = newRight - width
                newX = newLeft + paddleHalfWidth
            }
//            override existing values
            x = newX
            left = newLeft
            right = newRight

        }
//        Log.d("UPDATE X", "AFTER update l: $left, x: $x, r: $right")
    }


    fun draw(canvas: Canvas) {
        canvas.drawRect(left, top, right, bottom, color)
    }

    fun getRect(): Rect {
        return Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
    }
}