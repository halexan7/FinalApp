/*
* CS3013 - Mobile App Dev. - Summer 2022
* Instructor: Thyago Mota
* Student(s): Horace Alexander
* Description: Quiz Game, Final App
*/

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