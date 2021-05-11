package com.app.pong.movable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Ball(var x: Float, var y: Float, var speed: Float) {
    val SIZE = 25f
    val color = Paint().also { it.setARGB(123, 120, 5, 1) }

    var xSpeed = 0f
    var ySpeed = 0f

    init {
        val angle =
            1 / 2f * Math.PI.toFloat() + (if (Random.nextBoolean()) -1 else +1) * (Random.nextFloat() % 1 / 3 * Math.PI.toFloat()) + (if (Random.nextBoolean()) Math.PI.toFloat() else 0f)
        xSpeed = speed * sin(angle)
        ySpeed = speed * cos(angle)
    }

    fun update() {
        x += xSpeed
        y += ySpeed
    }

    fun reflect(side: Boolean) {

        if (side) xSpeed = -xSpeed
        else ySpeed = -ySpeed
    }

    fun draw(canvas: Canvas) {
        canvas.drawOval(RectF(x, y, SIZE + x, SIZE + y), color)
    }

    fun randomizedBounce(side: Boolean, random: Float) {
        val angle =
            1 / 2f * Math.PI.toFloat() + (if (Random.nextBoolean()) -1 else +1) * (Random.nextFloat() % random * Math.PI.toFloat()) + (if (xSpeed < 0) Math.PI.toFloat() else 0f)

        if (side) {
            xSpeed = -speed * sin(angle)
            ySpeed = speed * cos(angle)
        } else {
            xSpeed *= speed * sin(angle)
            ySpeed *= -speed * cos(angle)
        }
    }

    fun speedUp(factor: Float) {
        speed *= factor
        xSpeed *= factor
        ySpeed *= factor
    }
}