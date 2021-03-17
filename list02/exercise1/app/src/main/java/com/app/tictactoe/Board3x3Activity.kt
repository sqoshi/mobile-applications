package com.app.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Board3x3Activity : AppCompatActivity() {
    lateinit var buttons: Array<Array<Button>>
    lateinit var player1ScoreTextView: TextView
    lateinit var player2ScoreTextView: TextView
    private val size = 3

    private var player1Turn: Boolean = true
    private var roundCounter: Int = 0
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board3x3)


        player1ScoreTextView = findViewById(R.id.player1Score)
        player2ScoreTextView = findViewById(R.id.player2Score)

        buttons = Array(size) { r ->
            Array(size) { c ->
                initializeButton(r, c)
            }
        }

        val buttonReset: Button = findViewById(R.id.resetButton)
        buttonReset.setOnClickListener {
            Toast.makeText(applicationContext, "Game reset!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun initializeButton(r: Int, c: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button$r$c", "id", packageName))
        button.setOnClickListener {
            Toast.makeText(applicationContext, "button $r$c clicked", Toast.LENGTH_SHORT).show()
        }
        return button
    }

}