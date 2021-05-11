package com.app.lastmultiplayergame.game.components

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.app.lastmultiplayergame.game.data.Point

/**
 * Represents single brick in game.
 */
class Brick(
    var hp: Int,
    width: Float,
    height: Float,
    leftTopPoint: Point,
) {
    var color = Paint().also { it.setARGB(255, 22, 11, 66) }

    val left = leftTopPoint.x
    val top = leftTopPoint.y
    val bottom = top + height
    val right = left + width
    var alive = true

    init {
        paintBrick(hp)
    }

    /**
     * Decrement brick hp by one.
     */
    fun hit() {
        hp -= 1
        paintBrick(hp)
        if (hp <= 0) {
            alive = false
        }
    }

    /**
     * Draw single brick if brick not broken yet.
     */
    fun draw(canvas: Canvas) {
        if (hp > 0)
            canvas.drawRect(left, top, right, bottom, color)
    }

    /**
     * Color brick in accordance with its max hp.
     */
    private fun paintBrick(hp: Int) {
        when (hp) {
            1 -> {
                color.color = Color.parseColor("#3DB7E4")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
            2 -> {
                color.color = Color.parseColor("#FF8849")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
            3 -> {
                color.color = Color.parseColor("#69BE28")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
        }
    }
}