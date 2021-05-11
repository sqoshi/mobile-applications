package com.app.lastmultiplayergame.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.app.lastmultiplayergame.MainActivity
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.listeners.ScoreListener
import com.app.lastmultiplayergame.room.database.model.Achievement
import com.app.lastmultiplayergame.room.database.viewmodel.AchievementViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Thread.sleep

/**
 * Handles singleplayer game using room database.
 */
class SingleplayerGameActivity : AppCompatActivity(), ScoreListener {
    private lateinit var viewModel: AchievementViewModel

    private var message = " stage finished."
    private var columns = 3 // default
    private var rows = 6 // default
    private var mode = "EASY" //default
    private var round = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_game)
        game_view.setGameScoreListener(this)
        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)

        if (intent.extras != null) {
            val newMode = intent.extras!!.getString("mode")
            if (newMode != null) {
                mode = newMode
            }
            val newRound = intent.extras!!.getInt("round")
            if (newRound > 1) {
                round = newRound
            }
        }

        game_view.setColumns(columns)
        game_view.setRows(rows)
        game_view.setMode(mode)

        game_view.runThread()

    }

    /**
     * When player destroyed all blocks moves to next stage.
     */
    override fun whenGameEnd(score: Int) {
        if (score == rows * columns) {
            val nxts = nextStage(mode)
            if (nxts != null) {
                stageAlert(mode + message)
                sleep(1200)
                val int = Intent(applicationContext, SingleplayerGameActivity::class.java)
                int.putExtra("mode", nxts)
                int.putExtra("round", nextRound())
                startActivity(int)
            } else {
                stageAlert("You have finished singleplayer.")
                saveResult()
                sleep(3000)
                val int = Intent(applicationContext, MainActivity::class.java)
                startActivity(int)
            }

        }
    }

    /**
     * Saves result in local `room` database.
     */
    private fun saveResult() {
        val a = Achievement(
            0,
            "player",
            round,
        )
        viewModel.addAchievement(a)
    }

    /**
     * Alert about new stage.
     */
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

    /**
     * Finds next stage.
     */
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

    /**
     * Increments round
     */
    private fun nextRound(): Int {
        return round + 1
    }


}