package com.app.tictactoe

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.Math.pow
import java.util.*
import kotlin.math.pow

class Board3x3Activity : AppCompatActivity() {
    lateinit var buttons: Array<Array<Button>>
    lateinit var player1ScoreTextView: TextView
    lateinit var player2ScoreTextView: TextView

    private val size = 3
    private var winSequences: Set<Set<IntArray>> =
        setOf(
            setOf(intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(2, 2)),
            setOf(intArrayOf(0, 2), intArrayOf(1, 1), intArrayOf(2, 0)),

            setOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2)),
            setOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2)),
            setOf(intArrayOf(2, 0), intArrayOf(2, 1), intArrayOf(2, 2)),
            setOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0)),
            setOf(intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(2, 1)),
            setOf(intArrayOf(0, 2), intArrayOf(1, 2), intArrayOf(2, 2)),
        )
    private var player1Turn: Boolean = true
    private var roundCounter: Int = 0
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    private var player1Symbol: String = "X"
    private var player2Symbol: String = "O"

    private var player1Fields: MutableSet<IntArray> = mutableSetOf()
    private var player2Fields: MutableSet<IntArray> = mutableSetOf()


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
            Toast.makeText(applicationContext, "HARD RESET!!", Toast.LENGTH_SHORT).show()
            player1Turn = true
            roundCounter = 0
            player1Score = 0
            player2Score = 0
            updateScore(player1ScoreTextView, player1Score)
            updateScore(player2ScoreTextView, player2Score)
            resetFields()
            resetBoard()
        }


    }

    private fun initializeButton(r: Int, c: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button$r$c", "id", packageName))
        button.setOnClickListener {
            onButtonClick(button, r, c)
        }
        return button
    }

    private fun onButtonClick(button: Button, r: Int, c: Int) {
        if (!isFieldBusy(button)) {
            if (player1Turn) {
                button.text = player1Symbol
                button.setBackgroundColor(Color.parseColor("#5e60ce"))
                player1Turn = false
                player1Fields.add(intArrayOf(r, c))
                if (hasWin(player1Fields)) {
                    basicAlert(findViewById(R.id.resetButton), "Player1 has won")
                    player1Score += 1
                    updateScore(player1ScoreTextView, player1Score)
                }

            } else {
                button.text = player2Symbol
                button.setBackgroundColor(Color.parseColor("#64dfdf"))
                player1Turn = true
                player2Fields.add(intArrayOf(r, c))
                if (hasWin(player2Fields)) {
                    basicAlert(findViewById(R.id.resetButton), "Player2 has won")
                    player2Score += 1
                    updateScore(player2ScoreTextView, player2Score)
                }
            }

            if ((player1Fields.size + player2Fields.size).toDouble() == size.toDouble().pow(2.0)) {
                basicAlert(findViewById(R.id.resetButton), "Draw")
                roundCounter++
            }
        } else {
            Toast.makeText(applicationContext, "Field is busy.", Toast.LENGTH_SHORT).show()

        }
    }

    private fun hasWin(fields: MutableSet<IntArray>): Boolean {
        // I really can not find how to compare two Sets of Arrays in Kotlin.

        if (fields.size > 2) {
            for (seq in winSequences) {
                var counter = 0
                for (cords in seq) {
                    for (f in fields) {
                        if (cords.contentToString() == f.contentToString()) {
                            counter++
                        }
                        if (counter == size) {
                            roundCounter++
                            return true
                        }
                    }
                }

            }
        }

        return false
    }

    @SuppressLint("SetTextI18n")
    private fun updateScore(txtView: TextView, score: Int) {
        val nick = txtView.text.split(" ")[0]
        txtView.text = "$nick $score"
    }


    private fun isFieldBusy(button: Button): Boolean {
        if (button.text == "O" || button.text == "X") {
            return true
        }
        return false
    }


    private fun resetBoard() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                buttons[i][j].text = ""
                buttons[i][j].setBackgroundColor(Color.parseColor("#C61616"))
            }
        }
    }

    private fun resetFields() {
        player1Fields = mutableSetOf()
        player2Fields = mutableSetOf()
    }

    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        resetBoard()
        resetFields()
    }

    private fun basicAlert(view: View, message: String) {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Result")
            setCancelable(false)
            setMessage(message)
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }


    }


}