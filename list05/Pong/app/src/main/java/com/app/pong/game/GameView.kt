package com.app.pong

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.app.pong.models.Coordinates
import com.app.pong.models.Movement
import com.app.pong.models.PointCounter
import com.app.pong.movable.Ball
import com.app.pong.movable.Desk

class GameView(context: Context, private val mode: String, private val difficulty: String) :
    SurfaceView(context),
    SurfaceHolder.Callback {
    private val th: GameThread
    private lateinit var callback: PointCounter

    //    private lateinit var bot: BasicBot
    private lateinit var ball: Ball
    private lateinit var leftDesk: Desk
    private lateinit var rightDesk: Desk
    private lateinit var lPaddleMovement: Movement
    private lateinit var rPaddleMovement: Movement

    init {
        holder.addCallback(this)
        th = GameThread(holder, this)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        ball.draw(canvas)
        leftDesk.draw(canvas)
        rightDesk.draw(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        leftDesk = Desk(getDiffHeight(), width / 8f, height / 2f)
        rightDesk = Desk(getDiffHeight(), 7 * width / 8f, height / 2f)


        leftDesk.setLimits(Coordinates(0f, 0f, 0f, height.toFloat()))
        rightDesk.setLimits(Coordinates(0f, 0f, 0f, height.toFloat()))

        lPaddleMovement = Movement(leftDesk, 100f / height, 0.3f)
        rPaddleMovement = Movement(rightDesk, 100f / height, 0.3f)
        //set last positions to middle
        lPaddleMovement.onInput(0f, height / 2f - leftDesk.h / 2f)
        rPaddleMovement.onInput(0f, height / 2f - rightDesk.h / 2f)

        nextRound()

        th.isWorking = true
        th.start()
    }

    private fun getDiffSpeed(): Float {
        if (mode == "singleplayer") return 20f
        return when (difficulty) {
            "easy" -> 15f
            "medium" -> 20f
            "hard" -> 25f
            else -> 15f
        }
    }

    private fun getDiffHeight(): Float {
        if (mode == "singleplayer") return height / 3f
        return when (difficulty) {
            "easy" -> height / 2f
            "medium" -> height / 3f
            "hard" -> height / 4f
            else -> height / 2f

        }
    }

    private fun getSpeedUp(): Float {
        return when (difficulty) {
            "easy" -> 1.01f
            "medium" -> 1.02f
            "hard" -> 1.05f
            else -> 1.01f

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        th.isWorking = false
        th.join()
    }
}
