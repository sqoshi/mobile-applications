package com.app.lastmultiplayergame.game

import android.graphics.Canvas
import android.view.SurfaceHolder

/**
 * Periodically repaints canvas elements. ( holds surface (GAMEVIEW)
 */
class GameThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {
    var running: Boolean = false
    private val fps = 60
    private var canvas: Canvas? = null

    override fun run() {
        var startTime: Long
        var time: Long
        var wait: Long
        val target = (1000 / fps).toLong()

        while (running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.drawThis(canvas!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
//                        e.printStackTrace()
                    }
                }
            }

            time = (System.nanoTime() - startTime) / 1000000
            wait = target - time

            try {
                sleep(wait)
            } catch (e: Exception) {
//                e.printStackTrace()
            }
        }
    }
}