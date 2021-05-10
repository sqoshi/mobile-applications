package com.app.lastmultiplayergame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.lastmultiplayergame.game.setup.GameTypeSettingsActivity
import com.app.lastmultiplayergame.game.setup.LobbyActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var playerReference: DatabaseReference
    private var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance()
//        database.getReference("rooms/").setValue(null)


        editText = editTextName
        button = loginButton

        addPlayer()

        button.setOnClickListener {
            // log player in
            playerName = editText.text.toString()
            editText.setText("")
            addPlayer()
        }

    }

    private fun addPlayer() {
        if (playerName != "") {
            playerReference = database.getReference("players/$playerName")
            playerReference.setValue("")
            addEventListener()
        }
    }

    private fun addEventListener() {
        // read database
        playerReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //success -> move to lobby activity
                if (playerName != "") {
                    val int = Intent(applicationContext, GameTypeSettingsActivity::class.java)
                    int.putExtra("playerName", playerName)
                    startActivity(int)
                    finish()
                    button.isEnabled = true // to remove

                }
            }

            @SuppressLint("SetTextI18n")
            override fun onCancelled(error: DatabaseError) {
                button.text = "LOG IN"
                button.isEnabled = true
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
            }
        })
    }
}