package com.app.hangman

import android.R.attr
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
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
    var alreadyDiscoveredLetters = hashSetOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        hideStatusBarTop()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializeButtons()

        if (savedInstanceState != null) {
            onOrientationChange(savedInstanceState)
        } else {
            setUpGame()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("imageIndex", imageIndex)
        outState.putString("currentWord", currentWord)
        outState.putString(
            "alreadyDiscoveredLetters",
            alreadyDiscoveredLetters.joinToString(separator = " ")
        )

    }

    private fun onOrientationChange(savedInstanceState: Bundle) {
        imageIndex = savedInstanceState.getInt("imageIndex")
        displayNextHangmanImage(imageIndex)
        currentWord = savedInstanceState.getString("currentWord").toString()
        val alreadyDiscoveredLettersAsStr = savedInstanceState.getString("alreadyDiscoveredLetters")
        if (alreadyDiscoveredLettersAsStr != null) {
            alreadyDiscoveredLetters = alreadyDiscoveredLettersAsStr.split(" ").toHashSet()
        }
        showBlurredWord(currentWord)
        for (x in alreadyDiscoveredLetters) {
            showLetter(x)
        }

    }

    private fun hideStatusBarTop() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        actionBar?.hide()
        actionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.title = null;
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
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
        if (currentWord.contains(button.text)) {
            alreadyDiscoveredLetters.add(button.text.toString())
            showLetter(button.text)
        } else {
            displayNextHangmanImage()
        }
        if (hasLost()) {
            nextRoundRoutine("You Lost :(")
        }
        if (hasWin()) {
            nextRoundRoutine("You Won :)")

        }

    }

    private fun showLetter(letter: CharSequence) {
        val indexes = findIndexes(currentWord, letter.toString())
        val lin = findViewById<LinearLayout>(R.id.word_layout)

        for (i in indexes) {
            val btn = lin.findViewWithTag<Button>("wordCard$i")
            btn.setBackgroundColor(Color.BLACK)
        }
    }

    private fun displayNextHangmanImage(ind: Int = -999) {
        val moveIndex = if (ind == -999) {
            imageIndex += 1
            imageIndex
        } else {
            ind
        }

        binding.imageView.setImageResource(
            resources.getIdentifier(
                "hangman${moveIndex}",
                "drawable",
                packageName
            )
        )
    }

    private fun showBlurredWord(word: String) {
        val lyt = findViewById<LinearLayout>(R.id.word_layout)
        for ((i, c) in word.toCharArray().withIndex()) {
            val button = Button(this)
            button.tag = "wordCard$i"
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            params.setMargins(1, 1, 1, 1)
            button.layoutParams = params
            button.text = c.toString()
            button.setPadding(0, 0, 0, 0)
            button.setBackgroundColor(Color.WHITE)
            button.setTextColor(Color.WHITE)
            lyt.addView(button);
        }
    }

    private fun hasLost(): Boolean {
        return imageIndex == 13
    }

    private fun hasWin(): Boolean {
        return alreadyDiscoveredLetters.size == currentWord.toSet().size
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

    private fun setUpGame() {
        alreadyDiscoveredLetters = hashSetOf()
        imageIndex = 0
        displayNextHangmanImage(ind = imageIndex)
        currentWord = getRandomWord()
        showBlurredWord(currentWord)
    }

    private fun nextRoundRoutine(message: String) {
        basicAlert(message)
        val lyt = findViewById<LinearLayout>(R.id.word_layout)
        lyt.removeAllViewsInLayout()
        setUpGame()
    }

    private fun basicAlert(message: String) {
        Thread.sleep(1_000)
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Result")
            setMessage(message)
            show()
        }

    }
}
