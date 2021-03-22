package com.app.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

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
            Toast.makeText(applicationContext, "Game reset!", Toast.LENGTH_SHORT).show()
            player1Fields = mutableSetOf()
            player2Fields = mutableSetOf()
            player1Turn = true
            roundCounter = 0
            player1Score = 0
            player2Score = 0
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
//        Toast.makeText(applicationContext, "button ${button.id} clicked", Toast.LENGTH_SHORT).show()
        if (!isFieldBusy(button)) {
            if (player1Turn) {
                button.text = player1Symbol
                player1Turn = false
                player1Fields.add(intArrayOf(r, c))

                Toast.makeText(
                    applicationContext,
                    hasWin(player1Fields).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                button.text = player2Symbol
                player1Turn = true
                player2Fields.add(intArrayOf(r, c))

                Toast.makeText(
                    applicationContext,
                    hasWin(player2Fields).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Field is busy.", Toast.LENGTH_SHORT).show()

        }
    }

    private fun hasWin(fields: MutableSet<IntArray>): Boolean {
        // I really can not find how to compare two Sets of Arrays in Kotlin.

        if (fields.size > 2) {
            Log.i("info", "++++++++++++++++++FIELDS+++++++++++++++++++++++++")
            Log.i("info", fields.javaClass.name)
            for (seq in winSequences) {

                Log.i("info", "----------------------------------------")
                var counter = 0;
                for (cords in seq) {
                    Log.i("info", cords.contentToString())
                    for (f in fields) {

                        Log.i("info", f.contentToString())

                        if (cords.contentToString() == f.contentToString()) {
                            counter++
                            Log.i("info", counter.toString())
                        }
                        if (counter == size) {
                            return true
                        }
                    }
                }

            }
        }
        return false
    }

    private fun isFieldBusy(button: Button): Boolean {
        if (button.text == "O" || button.text == "X") {
            return true
        }
        return false
    }


}