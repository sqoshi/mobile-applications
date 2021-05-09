package com.app.lastmultiplayergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.lastmultiplayergame.game.GameView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GameView.ScoreListener {
    private lateinit var button: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var messageReference: DatabaseReference
    private var playerName = ""
    private var roomName = ""
    private var role = ""
    private var message = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        game_view.setGameScoreListener(this)

        button = pokeButton

        database = FirebaseDatabase.getInstance()


        val extras = intent.extras
        if (extras != null) {
            playerName = extras.getString("playerName", "").toString()
            roomName = extras.getString("roomName").toString()
        }

        button.setOnClickListener {
            button.isEnabled = false
            message = "$playerName: poked!"
            messageReference.setValue(message)
        }

        //listen incoming messages
        messageReference = database.getReference("rooms/$roomName/message")
        messageReference.setValue(message)
        addRoomEventListener()


    }

    override fun onGameEnd(score: Int) {
        Log.d("SCORE", "$score")
        if (score == 2){
            messageReference = database.getReference("rooms/$roomName/message")
            messageReference.setValue("$playerName WON")
        }
    }

    private fun addRoomEventListener() {
        messageReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("ROOOMS ROOMNAME", "----------------------------------")
//                Log.d("ROOOMS PLAYERNAME", "$playerName : $roomName")
//                Log.d("ROOOMS", snapshot.getValue(String::class.java).toString())
//                Log.d("ROOOMS ROOMNAME", "----------------------------------")
                if (roomName == playerName) {
                    button.isEnabled = !snapshot.getValue(String::class.java)!!.contains(roomName)
                } else {
                    button.isEnabled = snapshot.getValue(String::class.java)!!.contains(roomName)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageReference.setValue(message)
            }

        })
    }
}