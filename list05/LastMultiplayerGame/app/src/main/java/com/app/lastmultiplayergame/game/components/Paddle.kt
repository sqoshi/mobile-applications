package com.app.lastmultiplayergame.game.components

import android.graphics.*
import com.app.lastmultiplayergame.game.data.Point
import com.app.lastmultiplayergame.game.math.AuxiliaryOperations

/**
 * Paddle components moves to left, right  on screen touch.
 */
class Paddle(
    private val screenLeft: Int,
    screenTop: Int,
    private val screenRight: Int,
    screenBottom: Int
) {
    val color: Paint = Paint()

    // const
    private val width = AuxiliaryOperations.getHypotenuse(
        screenRight.toFloat(),
        screenBottom.toFloat()
    ) / 5f
    private val height = (screenBottom - screenTop) / 28.0f
    private val y = screenBottom - 1.5f * height  // y is const. not mutable
    private val top = y - height / 2.0f
    private val bottom = y + height / 2.0f

    var x = (screenRight - screenLeft) / 2.0f
    var left = x - width / 2.0f
    var right = x + width / 2.0f

    var speed = 10.0f

    init {
        color.color = Color.parseColor("#fee715")
//        color.setShadowLayer(120f, 120f, 120f, Color.BLACK)
        color.setShadowLayer(4f, 0f, 0f, Color.WHITE);
    }


    /**
     * Return left top point of paddle.
     */
    fun getLeftTopCords(): Point {
        return Point(left, top)

    }

    /**
     * Return right bottom point of paddle.
     */
    fun getRightBottomCords(): Point {
        return Point(right, bottom)

    }

    /**
     * Updates paddle position, but prevent abroad screen moves.
     */
    fun updateX(sense: Float) {
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
            // override existing values
            x = newX
            left = newLeft
            right = newRight

        }
    }

    /**
     * Draw paddle on canvas.
     */
    fun draw(canvas: Canvas) {
        canvas.drawRoundRect(RectF(left, top, right, bottom), 45f, 45f, color)
    }

}