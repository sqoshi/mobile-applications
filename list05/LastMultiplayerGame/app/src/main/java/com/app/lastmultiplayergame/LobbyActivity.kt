package com.app.lastmultiplayergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lobby.*
import java.util.*
import kotlin.collections.ArrayList

class LobbyActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var roomReference: DatabaseReference
    private var playerName = ""
    private var roomName = ""
    val roomsList = ArrayList<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowModifier.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_lobby)

        database = FirebaseDatabase.getInstance()

        playerName = intent.extras?.getString("playerName", "").toString()
        roomName = playerName

        listView = playerList

        button = createRoomButton

        // available rooms


        button.setOnClickListener {
            // create room and add yourself as first player.
//            button.text = getString(R.string.creating_room)
//            button.isEnabled = false
//            val int = Intent(applicationContext, GameActivity::class.java)
//            int.putExtra("roomName", roomName)
//            int.putExtra("playerName", playerName)
//            startActivity(int)
//            roomReference = database.getReference("rooms/$roomName/player1")
//            addRoomEventListener()
//            roomReference.setValue(playerName)
            val int = Intent(applicationContext, SettingsActivity::class.java)
            int.putExtra("roomName", roomName)
            int.putExtra("playerName", playerName)
            startActivity(int)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            roomName = roomsList[position].toString()
            roomReference = database.getReference("rooms/$roomName/player2")
            addRoomEventListener()
            roomReference.setValue(playerName)
        }

        addRoomsEventListener()
    }


    private fun addRoomEventListener() {
        roomReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                button.text = getString(R.string.create_room)
                button.isEnabled = true
                val int = Intent(applicationContext, GameActivity::class.java)
                int.putExtra("roomName", roomName)
                int.putExtra("playerName", playerName)
                startActivity(int)
            }

            override fun onCancelled(error: DatabaseError) {
                button.text = getString(R.string.create_room)
                button.isEnabled = true
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addRoomsEventListener() {
        roomReference = database.getReference("rooms")
        roomReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomsList.clear()
                val rooms = snapshot.children
                for (el in rooms) {
                    roomsList.add(el.key)
                    val adapter = ArrayAdapter<String>(
                        this@LobbyActivity,
                        R.layout.room,
                        R.id.text1,
                        roomsList
                    )
                    listView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
}