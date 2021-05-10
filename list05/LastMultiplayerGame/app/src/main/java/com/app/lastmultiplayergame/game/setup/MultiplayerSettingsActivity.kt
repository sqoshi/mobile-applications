package com.app.lastmultiplayergame.game.setup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.MultiplayerGameActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_settings.*

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
        addRowListener()
        playerName = intent.extras?.getString("playerName", "").toString()
        roomName = intent.extras?.getString("roomName", "").toString()
        button = openRoom


        button.setOnClickListener {
            // create room and add yourself as first player.
            button.text = getString(R.string.creating_room)
            button.isEnabled = false
            val rgc = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            val int = Intent(applicationContext, MultiplayerGameActivity::class.java)
            int.putExtra("roomName", roomName)
            int.putExtra("playerName", playerName)
            int.putExtra("columns", editTextColumn.text.toString())
            int.putExtra("rows", editTextRow.text.toString())
            int.putExtra("mode", rgc)
            Log.d("PUTTING", "PUTTING $rgc")
            startActivity(int)
            roomReference = database.getReference("rooms/$roomName/player1")
            roomReference.setValue(playerName)
            database.getReference("rooms/$roomName/columns")
                .setValue(editTextColumn.text.toString().toInt())
            database.getReference("rooms/$roomName/rows")
                .setValue(editTextRow.text.toString().toInt())
//            game_view.setRows(editTextRow.text.toString().toInt())
            database.getReference("rooms/$roomName/mode").setValue(rgc)
        }


    }

    private fun addRowListener() {
        rowsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("firebase", "DATACHANGED: ${dataSnapshot.value}")
//                rows = dataSnapshot.value.toString().toInt()
                if (dataSnapshot.value != null)
                    game_view.setRows(dataSnapshot.value.toString().toInt())
            }

            @SuppressLint("SetTextI18n")
            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "ERROR!")
            }
        })
    }


}