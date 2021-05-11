package com.app.lastmultiplayergame.game.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.lastmultiplayergame.R
import com.app.lastmultiplayergame.WindowAdjuster
import com.app.lastmultiplayergame.game.SingleplayerGameActivity
import kotlinx.android.synthetic.main.activity_game_type_settings.*

/**
 * Allow to choose game type singleplayer/ multiplayer
 */
class GameTypeSettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowAdjuster.hideMenu(window, actionBar, supportActionBar)
        setContentView(R.layout.activity_game_type_settings)

        singleplayerBtn.setOnClickListener {
            val int = Intent(applicationContext, SingleplayerGameActivity::class.java)
            startActivity(int)
            finish()
        }

        multiplayerBtn.setOnClickListener {
            val int = Intent(applicationContext, LobbyActivity::class.java)
            int.putExtra("playerName", intent.extras?.getString("playerName"))
            startActivity(int)
            finish()
        }

    }


}