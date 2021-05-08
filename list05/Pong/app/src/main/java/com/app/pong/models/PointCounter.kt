package com.app.pong.models

interface PointCounter {
    fun onPointCount(left: Boolean)
    fun onBounce()
}