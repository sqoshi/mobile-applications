package com.app.lastmultiplayergame.game.components

import android.graphics.Canvas
import android.graphics.Paint

class Brick(var hp: Int, val width: Float, val height: Float, val leftTopPoint: Point) {
    val color = Paint().also { it.setARGB(255, 22, 11, 66) }
    val left = leftTopPoint.x
    val top = leftTopPoint.y
    val bottom = top + height
    val right = left + width
    var alive = true

    fun hit() {
        hp -= 1
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
}