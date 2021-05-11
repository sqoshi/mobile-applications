package com.app.lastmultiplayergame.game.builders

import android.graphics.Canvas
import com.app.lastmultiplayergame.game.components.Brick
import com.app.lastmultiplayergame.game.data.Point

/**
 * Mason is responsible for building the wall of bricks.
 */
class Mason(
    private val screenLeft: Int,
    private val screenTop: Int,
    screenRight: Int,
    screenBottom: Int,
    var rowAmount: Int,
    var columnAmount: Int,
    var mode: String = "MEDIUM",
) {

    var brickWidth = ((screenRight.toFloat() - (columnAmount + 1) * 2f) / columnAmount.toFloat())
    var brickHeight = screenBottom / 30.0f
    var wall = buildWall()

    /**
     * Builds wall for current mode.
     */
    private fun buildWall(): MutableList<MutableList<Brick>> {
        when (mode) {
            "EASY" -> {
                return buildEasy()
            }
            "MEDIUM" -> {
                return buildMedium()
            }
            "HARD" -> {
                return buildHard()
            }
        }
        return buildEasy()
    }

    /**
     * Draws every single brick in wall in alive.
     */
    fun drawWall(canvas: Canvas) {
        for (row in wall) {
            for (brick in row) {
                if (brick.alive)
                    brick.draw(canvas)
            }
        }
    }

    /**
     * Count how many bricks is alive.
     */
    fun countBrokenBricks(): Int {
        var counter = 0
        for (row in wall) {
            for (brick in row) {
                if (!brick.alive)
                    counter += 1
            }
        }
        return counter
    }

    /**
     * Build easy stage.
     * Only 1hp bricks.
     */
    private fun buildEasy(): MutableList<MutableList<Brick>> {
        var currX = screenLeft.toFloat()
        var currY = screenTop.toFloat()
        val wall = mutableListOf<MutableList<Brick>>()
        for (i in 1..rowAmount) {
            currY += 2.0f
            val list = mutableListOf<Brick>()
            for (j in 1..columnAmount) {
                currX += 2f
                val b = Brick(1, brickWidth, brickHeight, Point(currX, currY))
                list.add(b)
                currX += brickWidth
            }
            wall.add(list)
            currX = screenLeft.toFloat()
            currY += brickHeight
        }
        return wall
    }

    /**
     * Build medium stage.
     * 1hp brick in even rows and 2hp in rest.
     */
    private fun buildMedium(): MutableList<MutableList<Brick>> {
        var currX = screenLeft.toFloat()
        var currY = screenTop.toFloat()
        val wall = mutableListOf<MutableList<Brick>>()
        for (i in 1..rowAmount) {
            currY += 2.0f
            val list = mutableListOf<Brick>()
            for (j in 1..columnAmount) {
                currX += 2f
                val hp = if (i % 2 == 0) 1 else 2
                val b = Brick(hp, brickWidth, brickHeight, Point(currX, currY))
                list.add(b)
                currX += brickWidth
            }
            wall.add(list)
            currX = screenLeft.toFloat()
            currY += brickHeight
        }
        return wall
    }

    /**
     * Build hard stage.
     * 2,3hp alternately.
     */
    private fun buildHard(): MutableList<MutableList<Brick>> {
        var currX = screenLeft.toFloat()
        var currY = screenTop.toFloat()
        val wall = mutableListOf<MutableList<Brick>>()
        for (i in 1..rowAmount) {
            currY += 2.0f
            val list = mutableListOf<Brick>()
            for (j in 1..columnAmount) {
                currX += 2f
                val hp = if (i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0) 2 else 3
                val b = Brick(hp, brickWidth, brickHeight, Point(currX, currY))
                list.add(b)
                currX += brickWidth
            }
            wall.add(list)
            currX = screenLeft.toFloat()
            currY += brickHeight
        }
        return wall
    }


}