package com.app.lastmultiplayergame.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AlertDialog
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.game.components.Ball
import com.app.lastmultiplayergame.game.builders.Mason
import com.app.lastmultiplayergame.game.components.Paddle
import com.app.lastmultiplayergame.game.data.ScreenCords
import com.app.lastmultiplayergame.game.listeners.ScoreListener
import com.google.firebase.database.*


class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private val thread: GameThread
    private lateinit var database: FirebaseDatabase
    private lateinit var paddle: Paddle
    private lateinit var ball: Ball
    private lateinit var mason: Mason
    private var mode: String? = null// "MEDIUM"
    private var rows: Int? = null
    private var columns: Int? = null
    private var maxScore: Int? = null
    var roomName = ""
    private val builder = AlertDialog.Builder(context)


    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        database = FirebaseDatabase.getInstance()

    }

    private var gameScoreListener: ScoreListener? = null
    fun setGameScoreListener(listener: ScoreListener) {
        gameScoreListener = listener
    }


    override fun surfaceCreated(holder: SurfaceHolder) {

        val scr = ScreenCords(left, top, right, bottom)

        maxScore = rows!! * columns!!
        paddle = Paddle(left, top, right, bottom)
        ball = Ball(right / 2.0f, bottom / 2f, 80f, scr)
        mason = Mason(left, top, right, bottom, rows!!, columns!!, mode!!)
//        waitingAlert()
//        showAlert()
//        runThread()
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }


    fun drawThis(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        canvas.drawColor(Color.parseColor("#101820"))
        paddle.draw(canvas)
        ball.draw(canvas)
        ball.update(paddle, mason.wall)
        mason.drawWall(canvas)
        gameScoreListener?.whenGameEnd(mason.countBrokenBricks())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
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

    fun runThread() {
        thread.running = true
        thread.start()

    }

    fun pauseThread(value: Boolean = false) {
        thread.running = value
        thread.join()
    }

    fun interruptThread() {
        thread.interrupt()
    }

    fun waitingAlert() {
        with(builder)
        {
            setTitle("Listener")
            setMessage("Waiting for next player ...")
        }
    }

    fun showAlert() {
        builder.show()
    }

    fun hideAlert() {
        val d = builder.show()
        d.dismiss()
        d.cancel()
        d.hide()
    }

    fun setRows(n: Int) {
        rows = n

    }

    fun setColumns(n: Int) {
        columns = n

    }

    fun setMode(id: String?) {
        mode = if (id !== null) id else "MEDIUM"
    }

}


