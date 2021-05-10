package com.app.lastmultiplayergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var roomReference: DatabaseReference
    private var playerName = ""
    private var roomName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowModifier.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_settings)

        database = FirebaseDatabase.getInstance()

        playerName = intent.extras?.getString("playerName", "").toString()
        roomName = intent.extras?.getString("roomName", "").toString()
        button = openRoom


        button.setOnClickListener {
            // create room and add yourself as first player.
            button.text = getString(R.string.creating_room)
            button.isEnabled = false
            val rgc = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            val int = Intent(applicationContext, GameActivity::class.java)
            int.putExtra("roomName", roomName)
            int.putExtra("playerName", playerName)
            int.putExtra("columns", editTextColumn.text.toString())
            int.putExtra("rows", editTextRow.text.toString())
            int.putExtra("mode", rgc)
            Log.d("PUTTING","PUTTING $rgc")
            startActivity(int)
            roomReference = database.getReference("rooms/$roomName/player1")
            roomReference.setValue(playerName)
        }

    }


}