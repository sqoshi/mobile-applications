package com.app.lastmultiplayergame.game.components

import android.graphics.Canvas
import android.util.Log

class Mason(
    val screenLeft: Int,
    val screenTop: Int,
    val screenRight: Int,
    val screenBottom: Int,
    val rowAmount: Int,
    val columnAmount: Int,
) {

    val brickWidth = (screenRight.toFloat() / columnAmount.toFloat() - (columnAmount.toFloat() + 1f)).toFloat()
    val brickHeight = screenBottom / 30.0f
    val wall = buildWall()

    private fun buildWall(): MutableList<MutableList<Brick>> {
        Log.d("BRICK CORDS", "SB: $screenBottom, SR: $screenRight , $rowAmount x $columnAmount ")
        var currX = screenLeft.toFloat()
        var currY = screenTop.toFloat()
        val wall = mutableListOf<MutableList<Brick>>()
        for (i in 1..rowAmount) {
            currY += 2.0f
            val list = mutableListOf<Brick>()
            for (j in 1..columnAmount) {
                currX += 2.0f
                Log.d("BRICK CORDS", "BW: $brickWidth BH: $brickHeight X:$currX Y:$currY")
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

    fun drawWall(canvas: Canvas) {
        for (row in wall) {
            for (brick in row) {
                if (brick.alive)
                    brick.draw(canvas)
            }
        }
    }


}