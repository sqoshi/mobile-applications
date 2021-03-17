package com.app.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var buttonBoard3x3: Button
    lateinit var buttonBoard5x5: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBoard3x3 = findViewById(R.id.button_3x3)
        buttonBoard5x5 = findViewById(R.id.button_5x5)

        buttonBoard3x3.setOnClickListener {
            val intent: Intent = Intent(applicationContext, Board3x3Activity::class.java)
            startActivity(intent)
        }
        buttonBoard5x5.setOnClickListener {
            val intent: Intent = Intent(applicationContext, Board5x5Activity::class.java)
            startActivity(intent)
        }
    }
}