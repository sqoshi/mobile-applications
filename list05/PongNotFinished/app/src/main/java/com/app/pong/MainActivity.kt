package com.app.pong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.app.pong.game.GameActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.multiplayerButton).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("diff", getDifficulty())
            intent.putExtra("mode", "multiplayer")
            startActivity(intent)
        }
        findViewById<Button>(R.id.singleplayerButton).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("diff", getDifficulty())
            intent.putExtra("mode", "singleplayer")
            startActivity(intent)
        }
    }

    private fun getDifficulty(): String {
        return when (findViewById<RadioGroup>(R.id.difficultyRadioGroup).checkedRadioButtonId) {
            R.id.radioEASYButton -> "easy"
            R.id.radioMEDIUMButton -> "medium"
            R.id.radioHARDButton -> "hard"
            else -> "easy"
        }
    }

}