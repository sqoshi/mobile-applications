package com.app.lastmultiplayergame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("player/")
//        myRef.setValue("Hello, asd!")
//        playerReference = database.getReference("players/$playerName")
//        playerReference.setValue("Hello, asd!")


        editText = editTextName
        button = loginButton


        if (playerName != "") {
            playerReference = database.getReference("players/$playerName")
            addEventListener()
            playerReference.setValue("")
        }

        button.setOnClickListener {
            // log player in
            playerName = editText.text.toString()
            editText.setText("")
            if (playerName != "") {
                button.text = "LOGGING IN"
                button.isEnabled = false
                playerReference = database.getReference("players/$playerName")
                playerReference.setValue("")
                addEventListener()
            }
        }

    }

    private fun addEventListener() {
        // read database
        playerReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //success -> move to lobby activity
                if (playerName != "") {
                    val int = Intent(applicationContext, LobbyActivity::class.java)
                    int.putExtra("playerName", playerName)
                    startActivity(int)
                    finish()
                    button.isEnabled = true // to remove

                }
            }

            override fun onCancelled(error: DatabaseError) {
                button.setText("LOG IN")
                button.isEnabled = true
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
            }
        })
    }
}