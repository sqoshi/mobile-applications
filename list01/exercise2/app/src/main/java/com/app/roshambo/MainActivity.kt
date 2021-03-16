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
    private var userChoice = ""
    private var computerChoice = ""
    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            val value = savedInstanceState.getInt("counter")
            binding.counter.text = value.toString()
            counter = value
            val compSaved = savedInstanceState.getString("computerChoice")
            val usrSaved = savedInstanceState.getString("userChoice")
            if (compSaved != null && usrSaved != null) {
                userChoice = usrSaved
                computerChoice = compSaved
            }

            val comp = moves.find { it.name == compSaved }
            val usr = moves.find { it.name == usrSaved }
            if (comp != null && usr != null) {
                binding.userChoice.setImageResource(usr.img)
                binding.computerChoice.setImageResource(comp.img)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
        outState.putString("computerChoice", computerChoice)
        outState.putString("userChoice", userChoice)

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
            updateCounter(1)
        } else if (userChoice == computerChoice) {
            val toast = Toast.makeText(binding.resultTextview.context, "Draw !", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
        } else {
            val toast = Toast.makeText(binding.resultTextview.context, "Loss !", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
            updateCounter(-1)
        }

    }

    private fun updateCounter(value: Int) {
        if (value == 0) {
            counter = 0
        } else {
            counter += value
        }
        binding.counter.text = counter.toString()
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
        updateCounter(0)
        binding.userChoice.setImageResource(0)
        binding.computerChoice.setImageResource(0)
    }


}