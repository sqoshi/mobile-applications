package com.app.lastmultiplayergame.game.math

import kotlin.math.pow
import kotlin.math.sqrt

class AuxiliaryOperations {
    companion object {

        /**
         * Computes
         */
        fun getHypotenuse(a: Float, b: Float): Float {
            return sqrt(a.toDouble().pow(2) + b.toDouble().pow(2)).toFloat()
        }

    }
}