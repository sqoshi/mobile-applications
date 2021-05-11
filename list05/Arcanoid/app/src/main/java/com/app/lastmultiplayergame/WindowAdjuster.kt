package com.app.lastmultiplayergame

import android.app.ActionBar
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * [(DRY)] Allow to not repeat code.
 */
class WindowAdjuster {
    companion object {
        /**
         * Allow to hide menubar,statusbar,actionbar.
         */
        fun hideMenu(
            window: Window,
            actionBar: ActionBar?,
            supportActionBar: androidx.appcompat.app.ActionBar?
        ) {
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