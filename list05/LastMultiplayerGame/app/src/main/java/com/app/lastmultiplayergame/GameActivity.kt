package com.app.lastmultiplayergame

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.app.lastmultiplayergame.game.ScoreListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), ScoreListener {
    private lateinit var database: FirebaseDatabase
    private lateinit var messageReference: DatabaseReference
    private lateinit var player2Reference: DatabaseReference
    private var playerName = ""
    private var roomName = ""
    private var message = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowModifier.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_game)
        game_view.setGameScoreListener(this)

        database = FirebaseDatabase.getInstance()


        val extras = intent.extras
        Log.d("MODE", "extras $extras")
        if (extras != null) {
            playerName = extras.getString("playerName", "").toString()
            roomName = extras.getString("roomName").toString()
//            val r = extras.getString("rows")?.toIntOrNull()
//            Log.d("MODE", /"SETTING $r ${extras.getString("rows")}" )
//
//            if (r != null) {
//                rows = r
//                game_view.setRows(rows)
//                Log.d("MODE", "SETTING $r")
//            }
//            val c = extras.getString("columns")?.toIntOrNull()
//            if (c != null) {
//                columns = c
//                game_view.setColumns(columns)
//                Log.d("MODE", "SETTING $c")
//
//            }
//            val m = extras.getString("mode")
//            Log.d("MODE", "MODE: $m")
//            game_view.setMode(m)
            if (playerName == roomName) {
                game_view.waitingAlert()
            }
//            game_view.createMason()

        }
//        Log.d("MODE", "extras $extras")


        //listen incoming messages
        messageReference = database.getReference("rooms/$roomName/message")
        player2Reference = database.getReference("rooms/$roomName/player2")
        addRoomEventListener()


    }


    override fun onGameEnd(score: Int) {
        if (score == 24) {
            messageReference = database.getReference("rooms/$roomName/message")
            messageReference.setValue("$playerName WON")
        }
    }

    private fun closeRoom() {
        val room = database.getReference("rooms/$roomName")
        room.setValue(null)

    }

    override fun onStop() {
        if (roomName == playerName)
            closeRoom()
        super.onStop()
    }

    private fun addRoomEventListener() {

        messageReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.getValue(String::class.java) != null) {
                    game_view.pauseThread()
                    basicAlert(snapshot.getValue(String::class.java))
                    closeRoom()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageReference.setValue(message)
            }

        })

        player2Reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.getValue(String::class.java) != null) {
                    game_view.runThread()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageReference.setValue(message)
            }

        })


    }


    private fun basicAlert(message: String?) {
        val builder = AlertDialog.Builder(this)
        val start = { dialog: DialogInterface, which: Int ->
            game_view.interruptThread()
            val int = Intent(applicationContext, LobbyActivity::class.java)
            int.putExtra("playerName", playerName)
            startActivity(int)
            parent.finish()
            finish()
            closeRoom()

        }
        val closeApp = { dialog: DialogInterface, which: Int ->
            moveTaskToBack(false);
            closeRoom()
            finish()
            parent.finish()
            exitProcess(-1)
        }
        with(builder)
        {
            setTitle("THE END")
            setMessage(message)
            setPositiveButton("LOBBY", DialogInterface.OnClickListener(function = start))
            setNegativeButton("EXIT", DialogInterface.OnClickListener(function = closeApp))
            setCancelable(false)
            show()
        }

    }


}