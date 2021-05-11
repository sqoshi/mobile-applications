package com.app.lastmultiplayergame.game.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.MultiplayerGameActivity
import com.app.lastmultiplayergame.game.data.Room
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lobby.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Activity holds list of actual rooms.
 * Players may move to create room activity or enter existing room.
 */
class LobbyActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var roomReference: DatabaseReference
    private var playerName = ""
    private var roomName = ""
    private var columns = 0
    private var rows = 0
    private var mode = ""
    val roomsList = ArrayList<Room>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_lobby)

        database = FirebaseDatabase.getInstance()

        playerName = intent.extras?.getString("playerName", "").toString()
        roomName = playerName
        listView = playerList
        button = createRoomButton


        button.setOnClickListener {
            val int = Intent(applicationContext, MultiplayerSettingsActivity::class.java)
            int.putExtra("roomName", roomName)
            int.putExtra("playerName", playerName)
            startActivity(int)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            roomName = roomsList[position].name
            mode = roomsList[position].mode.toString()
            columns = roomsList[position].columns!!
            rows = roomsList[position].rows!!

            roomReference = database.getReference("rooms/$roomName/player2")
            addRoomEventListener()
            roomReference.setValue(playerName)
        }

        addRoomsEventListener()
    }

    /**
     * Join multiplayer room, creates game activity connected to some room.
     */
    private fun addRoomEventListener() {
        roomReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                button.text = getString(R.string.create_room)
                button.isEnabled = true
                val int = Intent(applicationContext, MultiplayerGameActivity::class.java)
                int.putExtra("roomName", roomName)
                int.putExtra("playerName", playerName)
                int.putExtra("columns", columns.toString())
                int.putExtra("rows", rows.toString())
                int.putExtra("mode", mode)
                startActivity(int)
            }

            override fun onCancelled(error: DatabaseError) {
                button.text = getString(R.string.create_room)
                button.isEnabled = true
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    /**
     * Creates list of clickable rooms with details.
     * Actualize when new rooms is being added to firebase.
     */
    private fun addRoomsEventListener() {
        roomReference = database.getReference("rooms")
        roomReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomsList.clear()
                val rooms = snapshot.children
                for (el in rooms) {
                    val r = Room(el.key!!, null, null, null)
                    for (child in el.children) {
                        when {
                            child.key.toString() == "mode" ->
                                r.mode = child.value.toString()
                            child.key.toString() == "columns" ->
                                r.columns = child.value.toString().toInt()
                            child.key.toString() == "rows" ->
                                r.rows = child.value.toString().toInt()
                        }
                    }
                    roomsList.add(r)
                    val adapter = ArrayAdapter<Room>(
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