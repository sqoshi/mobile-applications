package com.app.lastmultiplayergame.game

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.listeners.ScoreListener
import com.app.lastmultiplayergame.game.setup.LobbyActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.system.exitProcess

class MultiplayerGameActivity : AppCompatActivity(), ScoreListener {
    private lateinit var database: FirebaseDatabase
    private lateinit var messageReference: DatabaseReference
    private lateinit var playerScoreReference: DatabaseReference
    private lateinit var player2Reference: DatabaseReference
    private var playerName = ""
    private var roomName = ""
    private var message = ""
    private var columns = 999 // should be null |/
    private var rows = 999


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_game)
        game_view.setGameScoreListener(this)
        game_view.roomName = intent.extras?.getString("roomName").toString()


        database = FirebaseDatabase.getInstance()

        val extras = intent.extras
        if (extras != null) {
            playerName = extras.getString("playerName", "").toString()
            roomName = extras.getString("roomName").toString()
            rows = extras.getString("rows")!!.toInt()
            columns = extras.getString("columns")!!.toInt()
            game_view.setMode(extras.getString("mode"))
            game_view.setRows(rows)
            game_view.setColumns(columns)
            if (playerName == roomName) {
                game_view.waitingAlert()
                game_view.showAlert()
            }

        }
        //listen incoming messages
        messageReference = database.getReference("rooms/$roomName/message")
        player2Reference = database.getReference("rooms/$roomName/player2")
        playerScoreReference = database.getReference("rooms/$roomName/$playerName/score")
        addRoomEventListener()


    }


    override fun whenGameEnd(score: Int) {
        playerScoreReference.setValue("$score")
        if (score == rows * columns) {
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
                    game_view.hideAlert()
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