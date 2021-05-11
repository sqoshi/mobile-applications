package com.app.lastmultiplayergame.game.data

/**
 * Room config object.
 */
class Room(val name: String, var mode: String?, var columns: Int?, var rows: Int?) {
    override fun toString(): String {
        return "$name [ $mode  $columns x $rows ]"
    }
}
