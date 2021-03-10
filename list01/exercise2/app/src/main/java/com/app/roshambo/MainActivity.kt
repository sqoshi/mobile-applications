package com.app.roshambo

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.roshambo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    data class Move(val name: String, val img: Int, val note: String)

    private var moves = listOf(
        Move("rock", R.drawable.rock, "Rock throwed!"),
        Move("paper", R.drawable.paper, "Paper throwed!"),
        Move("scissors", R.drawable.scissors, "Scissors throwed!")
    )
    private var userChoice = "";
    private var computerChoice = "";
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun showResultAndUpdateCounter() {
        // has user win?
        if ((userChoice == "rock" && computerChoice == "scissors") ||
            (userChoice == "paper" && computerChoice == "rock") ||
            (userChoice == "scissors" && computerChoice == "paper")
        ) {
            val toast = Toast.makeText(binding.resultTextview.context, "Win :)", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
            binding.counter.text = (binding.counter.text.toString().toInt() + 1).toString()
        } else if (userChoice == computerChoice) {
            val toast = Toast.makeText(binding.resultTextview.context, "Draw !", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
        } else {
            val toast = Toast.makeText(binding.resultTextview.context, "Loss !", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
            binding.counter.text = (binding.counter.text.toString().toInt() - 1).toString()
        }

    }

    private fun computerMove() {
        val el = moves.random()
        print("$el")
        computerChoice = el.name
        binding.computerChoice.setImageResource(el.img)
        showResultAndUpdateCounter()
    }

    fun rockButton(view: View) {
        val el = moves.find { it.name == "rock" }
        print("$el")
        if (el != null) {
            userChoice = el.name
            binding.userChoice.setImageResource(el.img)
        }
        computerMove()
    }

    fun paperButton(view: View) {
        val el = moves.find { it.name == "paper" }
        print("$el")
        if (el != null) {
            userChoice = el.name
            binding.userChoice.setImageResource(el.img)
        }
        computerMove()

    }

    fun scissorsButton(view: View) {
        val el = moves.find { it.name == "scissors" }
        print("$el")
        if (el != null) {
            userChoice = el.name
            binding.userChoice.setImageResource(el.img)
        }
        computerMove()
    }

    fun resetButton(view: View) {
        binding.counter.text = 0.toString()
        binding.userChoice.setImageResource(0)
        binding.computerChoice.setImageResource(0)
    }


}