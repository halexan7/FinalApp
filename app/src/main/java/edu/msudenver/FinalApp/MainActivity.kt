package edu.msudenver.FinalApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val name = edtPlayerName.text.toString()
        btnStartGame = findViewById(R.id.btnStartGame)

        //use ArrayAdapter to add items to spinner
        spin.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gameTypes)

        print(spin.adapter.toString())



        //initialize start game button
        //start activity and send the name with it
        btnStartGame.setOnClickListener{
            if (spin.selectedItem.toString().equals("True or False")){
                val intent = Intent(this, TrueOrFalseActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, MultipleRadioButtons::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }

        val fabCreate: FloatingActionButton = findViewById(R.id.fabCreate)
        fabCreate.setOnClickListener{
            val intent = Intent(this,HistoryScreen::class.java)
            startActivity(intent)
        }
    }
}