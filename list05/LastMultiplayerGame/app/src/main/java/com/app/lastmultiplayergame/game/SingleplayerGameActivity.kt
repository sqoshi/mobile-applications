package com.app.lastmultiplayergame.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.app.lastmultiplayergame.MainActivity
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.listeners.ScoreListener
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Thread.sleep


class SingleplayerGameActivity : AppCompatActivity(), ScoreListener {

    private var message = " stage finished."
    private var columns = 3
    private var rows = 6
    private var mode = "EASY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_game)
        game_view.setGameScoreListener(this)

        if (intent.extras != null) {
            val newMode = intent.extras!!.getString("mode")
            if (newMode != null) {
                mode = newMode
            }
        }

        game_view.setColumns(columns)
        game_view.setRows(rows)
        game_view.setMode(mode)

        game_view.runThread()

    }

    override fun whenGameEnd(score: Int) {
        if (score == rows * columns) {
            val nxts = nextStage(mode)
            if (nxts != null) {
                stageAlert(mode + message)
                sleep(1200)
                val int = Intent(applicationContext, SingleplayerGameActivity::class.java)
                int.putExtra("mode", nxts)
                startActivity(int)
            } else {
                stageAlert("Yo have finished singleplayer.")
                sleep(3000)
                val int = Intent(applicationContext, MainActivity::class.java)
                startActivity(int)
            }

        }
    }


    private fun stageAlert(message: String?) {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Result")
            setMessage(message)
            setCancelable(false)
            show()
        }

    }

    private fun nextStage(s: String): String? {
        return when (s) {
            "EASY" -> {
                "MEDIUM"
            }
            "MEDIUM" -> {
                "HARD"
            }
            else -> null
        }
    }


}