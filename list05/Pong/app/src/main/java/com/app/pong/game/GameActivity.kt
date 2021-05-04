package com.app.pong

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
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
        setContentView(R.layout.activity_main)

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
        gameContainer.addView(game)

        scoreTextView.text = "$leftPoints:$rightPoints"

    }

    override fun onPointCount(left: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onBounce() {
        TODO("Not yet implemented")
    }

}