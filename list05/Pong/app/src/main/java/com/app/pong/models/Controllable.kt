package com.app.pong.models

interface Controllable {
    public fun updatePosition(x: Float, y: Float)
    public fun setLimits(coordinates: Coordinates)
    public fun getX(): Float
    public fun getY(): Float
}


data class Coordinates(val xu: Float, val xd: Float, val yu: Float, val yd: Float)


class Movement(
    private val element: Controllable,
    private val coefficient: Float,
    private val drag: Float
) {
    private var lastX = 0f
    private var lastY = 0f

    private var xAcc = 0f
    private var yAcc = 0f

    fun onInput(x: Float, y: Float) {
        lastX = x
        lastY = y
    }

    fun update() {
        xAcc = (lastX - element.getX()) * coefficient
        yAcc = (lastY - element.getY()) * coefficient

        if (xAcc > 0) xAcc -= if (xAcc > drag) drag else xAcc
        else xAcc += if (-xAcc > drag) drag else xAcc
        if (yAcc > 0) yAcc -= if (yAcc > drag) drag else yAcc
        else yAcc += if (-yAcc > drag) drag else yAcc

        element.let {
            it.updatePosition(it.getX() + xAcc, it.getY() + yAcc)
        }

    }
}