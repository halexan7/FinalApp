package edu.msudenver.FinalApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    lateinit var spin: Spinner
    lateinit var edtPlayerName: EditText
    lateinit var btnStartGame: Button

    //add values to the spinner
    val gameTypes = arrayOf<String?>("True or False", "Multiple Choice")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get reference to view objects
        spin = findViewById(R.id.spnGameSelection)
        edtPlayerName = findViewById(R.id.edtPlayerName)
        btnStartGame = findViewById(R.id.btnStartGame)

        //use ArrayAdapter to add items to spinner
        spin.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gameTypes)



    }
}