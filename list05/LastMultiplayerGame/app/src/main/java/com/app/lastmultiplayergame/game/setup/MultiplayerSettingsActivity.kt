package com.app.lastmultiplayergame.game.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.MultiplayerGameActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Handles activity responsible for multiplayer room setup.
 */
class MultiplayerSettingsActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var roomReference: DatabaseReference
    private lateinit var rowsReference: DatabaseReference
    private var playerName = ""
    private var roomName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_settings)

        database = FirebaseDatabase.getInstance()
        rowsReference = database.getReference("rooms/$roomName/rows")
        playerName = intent.extras?.getString("playerName", "").toString()
        roomName = intent.extras?.getString("roomName", "").toString()
        button = openRoom


        button.setOnClickListener {
            // create room and add yourself as first player.
            button.text = getString(R.string.creating_room)
            button.isEnabled = false
            val rgc = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            startMultiplayerGame(rgc)
            pushConfiguration(rgc)
        }


    }

    /**
     * Starts new activity to handle multiplayer game.
     */
    private fun startMultiplayerGame(rgc: String) {
        val int = Intent(applicationContext, MultiplayerGameActivity::class.java)
        int.putExtra("roomName", roomName)
        int.putExtra("playerName", playerName)
        int.putExtra("columns", editTextColumn.text.toString())
        int.putExtra("rows", editTextRow.text.toString())
        int.putExtra("mode", rgc)
        startActivity(int)
    }


    /**
     * Saves game configuration in database.
     */
    private fun pushConfiguration(rgc: String){
        roomReference = database.getReference("rooms/$roomName/player1")
        roomReference.setValue(playerName)
        database.getReference("rooms/$roomName/columns")
            .setValue(editTextColumn.text.toString().toInt())
        database.getReference("rooms/$roomName/rows")
            .setValue(editTextRow.text.toString().toInt())
        database.getReference("rooms/$roomName/mode").setValue(rgc)
    }


}