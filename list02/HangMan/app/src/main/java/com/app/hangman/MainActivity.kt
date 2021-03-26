package com.app.hangman

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.hangman.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var letters: Set<String> =
        "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ").toSet()

    lateinit var currentWord: String
    private lateinit var binding: ActivityMainBinding
    var imageIndex = 0
    var discovered = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        currentWord = getRandomWord()
        initializeButtons()
        displayHiddenWord(currentWord)

    }

    private fun getRandomWord(): String {
        return resources.getStringArray(R.array.words).random()
    }

    private fun initializeButtons() {
        for (letter in letters) {
            val button: Button =
                findViewById(resources.getIdentifier("button$letter", "id", packageName))
            button.setOnClickListener {
                onButtonClick(button)
            }
        }

    }

    private fun onButtonClick(button: Button) {
        Log.i("info", currentWord + " " + button.text)
        if (currentWord.contains(button.text)) {
            val indexes = findIndexes(currentWord, button.text.toString())
            val lin = findViewById<LinearLayout>(R.id.word_layout)

            for (i in indexes) {
                val btn = lin.findViewWithTag<Button>("wordCard$i")
                btn.setBackgroundColor(Color.BLACK)
            }


        } else {
            imageIndex++;
            binding.imageView.setImageResource(
                resources.getIdentifier(
                    "hangman${imageIndex}",
                    "drawable",
                    packageName
                )
            )
        }
    }

    private fun displayHiddenWord(word: String) {
        val constraintLayout = findViewById<LinearLayout>(R.id.word_layout)
        for ((i, c) in word.toCharArray().withIndex()) {
            val button = Button(this)
            button.tag = "wordCard$i"
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button.text = c.toString()
            button.setBackgroundColor(Color.WHITE)
            button.setTextColor(Color.WHITE)
            constraintLayout.addView(button);
        }
    }

    private fun hasLost(): Boolean {
        return imageIndex == 13
    }

    private fun hasWin(): Boolean {
        return discovered == currentWord.length
    }

    private fun findIndexes(word: String, guess: String): Set<Int> {
        val result = hashSetOf<Int>()
        var index = word.indexOf(guess)
        while (index >= 0) {
            result.add(index)
            index = word.indexOf(guess, index + 1)
        }
        return result

    }

}