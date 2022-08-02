package edu.msudenver.FinalApp

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TrueOrFalseActivity: AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    private lateinit var trueOrFalseQuestion: TextView
    private lateinit var checkboxTrue: CheckBox
    private lateinit var checkBoxFalse: CheckBox
    private lateinit var tofbtnSubmit: Button
    private lateinit var txtTick: TextView

    private val questionList = listOf<TrueOrFalseQuestion>(
        TrueOrFalseQuestion("Seahorses have stomachs for the absorption of nutrients from food", false),
        TrueOrFalseQuestion("The letter H is between letters G and J on a keyboard", true),
        TrueOrFalseQuestion("Camels have three sets of eyelashes", true),
        TrueOrFalseQuestion("There are five zeros in one hundred thousand", true),
        TrueOrFalseQuestion("If you add the two numbers on the opposite sides of dice together, the answer is always 7", true),
        TrueOrFalseQuestion("The deepest part of the ocean is about 36,200 feet down (11,030 m) which is more like 25 Empire State Buildings", true),
        TrueOrFalseQuestion("It takes a sloth two weeks to digest its food.", true),
        TrueOrFalseQuestion("A group of jellyfish is not a herd, or a school, or a flock; it's called a smack.", true)
    )

    private var currentIndex = 0
    private var correct = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trueorfalse)

        val playerName = intent.getStringExtra("name")

        val dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase

        trueOrFalseQuestion = findViewById(R.id.trueOrFalseQuestion)
        checkboxTrue = findViewById(R.id.checkboxTrue)
        checkBoxFalse = findViewById(R.id.checkboxFalse)

        updateQuestion()

        tofbtnSubmit = findViewById(R.id.tofbtnSubmit)
        tofbtnSubmit.setOnClickListener() {
            checkAnswer()
            currentIndex++
            if (currentIndex == questionList.size) {
                if (playerName != null) {
                    endGame((correct / questionList.size).toDouble(), playerName)
                } else
                    endGame((correct / questionList.size).toDouble(), "No Name Provided")
            } else {
                updateQuestion()
            }
        }

        txtTick = findViewById(R.id.toftxtTick)

    }



    private fun updateQuestion(){
        val questionText = questionList[currentIndex].question
        trueOrFalseQuestion.text = questionText
    }

    private fun checkAnswer(){
        checkboxTrue = findViewById(R.id.checkboxTrue)
        checkBoxFalse = findViewById(R.id.checkboxFalse)

        if(checkboxTrue.isChecked){
            if (questionList[currentIndex].answer){
                correct++
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
        }
        else if(checkBoxFalse.isChecked){
            if (!questionList[currentIndex].answer){
                correct++
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun endGame(score: Double, name: String){
        try {
            db.execSQL(
                """
                        INSERT INTO scores VALUES 
                ("${DBHelper.ISO_FORMAT.format(Date())}", "${name}", "True or False", ${score})
                    """
            )
            Toast.makeText(
                this,
                "Your score of ${score} was recorded!",
                Toast.LENGTH_SHORT
            ).show()
        } catch (ex: Exception) {
            Toast.makeText(
                this,
                "Exception when trying to record your score!",
                Toast.LENGTH_SHORT
            ).show()
        }
        finish()
    }
}