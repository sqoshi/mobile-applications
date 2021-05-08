package com.app.pong.movable

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.app.pong.models.Controllable
import com.app.pong.models.Coordinates

class Desk(private var x: Float, private var y: Float, var h: Float, var w: Float = 50f) :
    Controllable {
    private val color = Paint().also { it.setARGB(156, 1, 234, 1) }
    private lateinit var board: Coordinates
    fun draw(canvas: Canvas) {
        canvas.drawRect(RectF(x, y + h / 2f, x + w, y + 1.5f * h), color)
    }

    override fun updatePosition(x: Float, y: Float) {
        if (y + h / 2f > board.yu && y - h / 2f < board.yd)
            this.y = y - h / 2f
    }

    override fun setLimits(coordinates: Coordinates) {
        board = coordinates
    }

    override fun getX(): Float {
        return x
    }

    override fun getY(): Float {
        return y
    }

}