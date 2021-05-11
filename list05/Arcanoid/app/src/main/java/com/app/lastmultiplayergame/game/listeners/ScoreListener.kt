package com.app.lastmultiplayergame.game.listeners

/**
 * Allow to listener to score in game.
 */
interface ScoreListener {
    fun whenGameEnd(score: Int)
}
