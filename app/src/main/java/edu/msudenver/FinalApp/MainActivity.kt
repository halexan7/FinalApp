package edu.msudenver.FinalApp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var spin: Spinner
    private lateinit var edtPlayerName: EditText
    private lateinit var btnStartGame: Button

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

        //initialize start game button
        //start activity and send the name with it
        btnStartGame.setOnClickListener{
            if (spin.selectedItem.toString().equals("True or False") && !(TextUtils.isEmpty(edtPlayerName.text.toString()))){
                val intent = Intent(this, TrueOrFalseActivity::class.java)
                intent.putExtra("name", edtPlayerName.text.toString())
                startActivity(intent)
            }
            else if (spin.selectedItem.toString().equals("Multiple Choice") && !(TextUtils.isEmpty(edtPlayerName.text.toString()))) {
                val intent = Intent(this, MultipleRadioButtons::class.java)
                intent.putExtra("name", edtPlayerName.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }
        }

        val fabCreate: FloatingActionButton = findViewById(R.id.fabCreate)
        fabCreate.setOnClickListener{
            val intent = Intent(this,HistoryScreen::class.java)
            startActivity(intent)
        }
    }
}