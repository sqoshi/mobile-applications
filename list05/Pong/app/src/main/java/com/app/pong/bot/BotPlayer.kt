package com.app.pong.bot

import com.app.pong.movable.Ball
import java.util.*

class BotPlayer(private var ball: Ball, private val diff: String) {
    private var moves = LinkedList<Pair<Float, Float>>()

    init {
        for (i in 0 until getDiffIterationSize()) moves.add(Pair(ball.x, ball.y))
    }

    fun update(): Pair<Float, Float> {
        moves.add(
            Pair(
                ball.x + getInFrontItersSize() * ball.xSpeed,
                ball.y + getInFrontItersSize() * ball.ySpeed
            )
        )
        return moves.removeFirst()
    }

    private fun getDiffIterationSize(): Int {
        return when (diff) {
            "easy" -> 30
            "medium" -> 10
            "hard" -> 1
            else -> 30
        }
    }

    private fun getInFrontItersSize(): Int {
        return when (diff) {
            "easy" -> 0
            "medium" -> 2
            "hard" -> 5
            else -> 0
        }
    }
}