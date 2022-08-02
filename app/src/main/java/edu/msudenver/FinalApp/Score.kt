package edu.msudenver.FinalApp

import java.util.*

data class Score(
    var id: Int,
    var date: Date,
    var playername: String,
    var game: String,
    var value: Double,
) {
}