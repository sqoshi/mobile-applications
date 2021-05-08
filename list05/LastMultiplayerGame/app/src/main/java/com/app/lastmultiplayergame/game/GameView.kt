package com.app.lastmultiplayergame.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.app.lastmultiplayergame.game.components.Ball
import com.app.lastmultiplayergame.game.components.Mason
import com.app.lastmultiplayergame.game.components.Paddle
import com.app.lastmultiplayergame.game.components.ScreenCords

class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private val thread: GameThread
    private lateinit var paddle: Paddle
    private lateinit var ball: Ball
    private lateinit var mason: Mason

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        val scr = ScreenCords(left, top, right, bottom)
        paddle = Paddle(left, top, right, bottom)
        ball = Ball(right / 2.0f, bottom / 2f, 80f, scr)
        mason = Mason(left, top, right, bottom, 4, 6)
        Log.d("paddle", "SCREEN :" + (left + top + right + bottom).toString())
        thread.running = true
        thread.start()

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }

    fun drawThis(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        Log.d("DRAWING", "Screen cords: [L: $left T:$top")
        Log.d("DRAWING", "Screen cords: [R: $right B:$bottom")
        Log.d("paddle", paddle.width.toString())
        canvas.drawColor(Color.WHITE)
        paddle.draw(canvas)
        ball.draw(canvas)
        ball.update(paddle, mason.wall)
        mason.drawWall(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("EVENT CORDS", "EVENT ${event.x}")
        Log.d("paddle", "paddle ${paddle.getCenterCords()}")

        val screenXCenter = width / 2.0f

        var sense = 0.0f
        if (event.x < screenXCenter) {
            sense = -1.0f
        } else if (event.x > screenXCenter) {
            sense = 1.0f
        }
        paddle.updateX(sense)


        return true
    }
}