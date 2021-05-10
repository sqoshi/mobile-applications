package com.app.lastmultiplayergame

import android.app.ActionBar
import android.view.View
import android.view.Window
import android.view.WindowManager

class WindowAdjuster {
    companion object {
        fun hideMenu(window: Window, actionBar: ActionBar?, supportActionBar: androidx.appcompat.app.ActionBar?) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
            actionBar?.hide()
            actionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.hide()
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.title = null
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

    }
}