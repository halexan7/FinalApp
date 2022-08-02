package edu.msudenver.FinalApp

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MultipleRadioButtons: AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    private lateinit var multipleradioquestion: TextView
    private lateinit var radioBtnOne: RadioButton
    private lateinit var radioBtnTwo: RadioButton
    private lateinit var radioBtnThree: RadioButton
    private lateinit var radioBtnFour: RadioButton
    private lateinit var btnSubmit: Button
    private lateinit var txtTick: TextView

    private var currentIndex = 0
    private var correct = 0

    val list1 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("3", false),
        MultipleChoiceAnswers("5", false),
        MultipleChoiceAnswers("6", true),
        MultipleChoiceAnswers("10", false)
    )

    val list2 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Dark chocolate", false),
        MultipleChoiceAnswers("Peanut butter", false),
        MultipleChoiceAnswers("Canned tuna", false),
        MultipleChoiceAnswers("Honey", true)
    )

    val list3 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Not to Be Reproduced", false),
        MultipleChoiceAnswers("Personal Values", false),
        MultipleChoiceAnswers("The Lovers", false),
        MultipleChoiceAnswers("The Lost Jockey", true)
    )

    val list4 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Nick Lachey", false),
        MultipleChoiceAnswers("Justin Timberlake", true),
        MultipleChoiceAnswers("Shawn Stockman", false),
        MultipleChoiceAnswers("AJ McLean", false)
    )

    private val questionList = listOf<MultipleChoiceQuestion>(
        MultipleChoiceQuestion("How many Infinity Stones are there?", list1),
        MultipleChoiceQuestion("What is the only food that cannot go bad?", list2),
        MultipleChoiceQuestion("Which was René Magritte’s first surrealist painting?", list3),
        MultipleChoiceQuestion("What 90s boy band member bought Myspace in 2011?", list4)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipleradio)

        val playerName = intent.getStringExtra("name")

        val dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase

        multipleradioquestion = findViewById(R.id.MultipleRadioQuestion)
        radioBtnOne = findViewById(R.id.radioBtnOne)
        radioBtnTwo = findViewById(R.id.radioBtnTwo)
        radioBtnThree = findViewById(R.id.radioBtnThree)
        radioBtnFour = findViewById(R.id.radioBtnFour)

        updateQuestion()

        btnSubmit = findViewById(R.id.MultipleRadioSubmit)
        btnSubmit.setOnClickListener(){
            checkAnswer()
            currentIndex++
            if (currentIndex == questionList.size - 1){
                if (playerName != null) {
                    endGame((correct / questionList.size).toDouble(), playerName)
                }
                else
                    endGame((correct / questionList.size).toDouble(), "No Name Provided")
            }
            else{
                updateQuestion()
            }
        }

        txtTick = findViewById(R.id.multipleRadioTick)
    }

    private fun updateQuestion(){
            val questionText = questionList[currentIndex].question
            multipleradioquestion.text = questionText

            radioBtnOne = findViewById(R.id.radioBtnOne)
            radioBtnTwo = findViewById(R.id.radioBtnTwo)
            radioBtnThree = findViewById(R.id.radioBtnThree)
            radioBtnFour = findViewById(R.id.radioBtnFour)

            radioBtnOne.setText(questionList[currentIndex].answer[0].answer)
            radioBtnTwo.setText(questionList[currentIndex].answer[1].answer)
            radioBtnThree.setText(questionList[currentIndex].answer[2].answer)
            radioBtnFour.setText(questionList[currentIndex].answer[3].answer)
    }

    private fun checkAnswer(){
        radioBtnOne = findViewById(R.id.radioBtnOne)
        radioBtnTwo = findViewById(R.id.radioBtnTwo)
        radioBtnThree = findViewById(R.id.radioBtnThree)
        radioBtnFour = findViewById(R.id.radioBtnFour)

        if (radioBtnOne.isChecked) {
            if (questionList[currentIndex].answer[0].isCorrect) {
                correct++
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
        }
        else if (radioBtnTwo.isChecked) {
            if (questionList[currentIndex].answer[1].isCorrect) {
                correct++
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
        }
        else if (radioBtnThree.isChecked) {
            if (questionList[currentIndex].answer[2].isCorrect) {
                correct++
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "$correct / ${currentIndex + 1}", Toast.LENGTH_SHORT).show()
        }
        else if (radioBtnFour.isChecked) {
            if (questionList[currentIndex].answer[3].isCorrect) {
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
                ("${DBHelper.ISO_FORMAT.format(Date())}", "${name}", "Multiple Choice", ${score})
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