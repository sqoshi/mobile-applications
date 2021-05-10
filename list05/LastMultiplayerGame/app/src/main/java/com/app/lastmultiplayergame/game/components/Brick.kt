package com.app.lastmultiplayergame.game.components

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.app.lastmultiplayergame.game.data.Point

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

    fun hit() {
        hp -= 1
        paintBrick(hp)
        if (hp <= 0) {
            alive = false
        }
    }

    fun draw(canvas: Canvas) {
        if (hp > 0)
            canvas.drawRect(left, top, right, bottom, color)
    }

    fun getHP(): Int {
        return hp
    }

    private fun paintBrick(hp: Int) {
        when (hp) {
            1 -> {
                color.color = Color.parseColor("#5CE5D5")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
            2 -> {
                color.color = Color.parseColor("#B8FB3C")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
            3 -> {
                color.color = Color.parseColor("#FCF340")
                color.setShadowLayer(4f, 0f, 0f, color.color);
            }
        }
    }
}