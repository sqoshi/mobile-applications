package com.app.pong.game

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import com.app.pong.R
import com.app.pong.models.PointCounter

class GameActivity : AppCompatActivity(), PointCounter {
    private lateinit var difficulty: String
    private lateinit var mode: String

    private var leftPoints = 0
    private var rightPoints = 0

    private var points = 0
    private var top = 0

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        closeOptionsMenu()

        difficulty = intent?.getStringExtra("diff").toString()
        mode = intent?.getStringExtra("mode").toString()

        prefs = getPreferences(Context.MODE_PRIVATE)

        top = prefs.getInt(
            when (difficulty) {
                "easy" -> "te"
                "medium" -> "tm"
                "hard" -> "th"
                else -> "te"
            }, 0
        )

        val game = GameView(this, mode, difficulty)
        game.setPointCounter(this)
        Log.d("XD", game.toString())
        val gc = findViewById<FrameLayout>(R.id.gameContainer)
        Log.d("XD", gc.toString())

        gc.addView(game)
        val scoreText = findViewById<TextView>(R.id.scoreText)
        scoreText.text = "$leftPoints:$rightPoints"

    }

    override fun onPointCount(left: Boolean) {
        if (mode == "multiplayer") {
            if (left) rightPoints++
            else leftPoints++

            runOnUiThread {
                val scoreText = findViewById<TextView>(R.id.scoreText)
                scoreText.text = "$leftPoints:$rightPoints"
            }
        } else {
            points = 0
            runOnUiThread {
                val scoreText = findViewById<TextView>(R.id.scoreText)
                scoreText.text = "$points\r\nTop: $top"
            }
        }
    }

    override fun onBounce() {
        if (mode == "singleplayer") {
            if (++points > top) {
                top = points
                with(prefs.edit()) {
                    putInt(
                        when (difficulty) {
                            "easy" -> "te"
                            "medium" -> "tm"
                            "hard" -> "th"
                            else -> "te"
                        }, points
                    )
                    apply()
                }
            }

            runOnUiThread {
                val scoreText = findViewById<TextView>(R.id.scoreText)
                scoreText.text = "$points\r\nTop: $top"
            }
        }
    }
}