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

//    What is the highest-grossing video game franchise to date?
    val list3 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Mario", false),
        MultipleChoiceAnswers("Pokemon", true),
        MultipleChoiceAnswers("Call of Duty", false),
        MultipleChoiceAnswers("Street Fighter", false)
    )

    val list4 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Nick Lachey", false),
        MultipleChoiceAnswers("Justin Timberlake", true),
        MultipleChoiceAnswers("Shawn Stockman", false),
        MultipleChoiceAnswers("AJ McLean", false)
    )



//    What is the most visited tourist attraction in the world?
    val list5 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Eiffel Tower", true),
        MultipleChoiceAnswers("Statue of Liberty", false),
        MultipleChoiceAnswers("Great Wall of China", false),
        MultipleChoiceAnswers("Colosseum", false)
    )


//    What’s the heaviest organ in the human body?
    val list6 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Brain", false),
        MultipleChoiceAnswers("Liver", true),
        MultipleChoiceAnswers("Skin", false),
        MultipleChoiceAnswers("Heart", false)
    )


    //    Who made the third most 3-pointers in the Playoffs in NBA history?
    val list7 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Stephen Curry", true),
        MultipleChoiceAnswers("Ray Allen", false),
        MultipleChoiceAnswers("Klay Thompson", false),
        MultipleChoiceAnswers("LeBron James", false)
    )


    //    Which of these EU countries does not use the euro as its currency?
    val list8 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Poland", false),
        MultipleChoiceAnswers("Denmark", false),
        MultipleChoiceAnswers("Sweden", false),
        MultipleChoiceAnswers("All of the above", true)
    )

    //    Which US city is the sunniest major city and sees more than 320 sunny days each year?
    val list9 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Phoenix", true),
        MultipleChoiceAnswers("Miami", false),
        MultipleChoiceAnswers("San Francisco", false),
        MultipleChoiceAnswers("Austin", false)
    )

    //    Which fast food restaurant has the largest number of retail locations in the world?
    val list10 = mutableListOf<MultipleChoiceAnswers>(
        MultipleChoiceAnswers("Jack In The Box", false),
        MultipleChoiceAnswers("Chipotle", false),
        MultipleChoiceAnswers("Subway", false),
        MultipleChoiceAnswers("McDonald's", true)
    )

    private val questionList = listOf<MultipleChoiceQuestion>(
        MultipleChoiceQuestion("How many Infinity Stones are there?", list1),
        MultipleChoiceQuestion("What is the only food that cannot go bad?", list2),
        MultipleChoiceQuestion("What is the highest-grossing video game franchise to date?", list3),
        MultipleChoiceQuestion("What 90s boy band member bought Myspace in 2011?", list4),
        MultipleChoiceQuestion("What is the most visited tourist attraction in the world?", list5),
        MultipleChoiceQuestion("What’s the heaviest organ in the human body?", list6),
        MultipleChoiceQuestion("Who made the third most 3-pointers in the Playoffs in NBA history?", list7),
        MultipleChoiceQuestion("Which of these EU countries does not use the euro as its currency?", list8),
        MultipleChoiceQuestion("Which US city is the sunniest major city and sees more than 320 sunny days each year?", list9),
        MultipleChoiceQuestion("Which fast food restaurant has the largest number of retail locations in the world?", list10),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipleradio)

        val playerName = intent.getStringExtra("name")

        var count: Double

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
            count = checkAnswer().toDouble()
            currentIndex++
            if (currentIndex == questionList.size){
                if (playerName != null) {
                    endGame((count / questionList.size).toDouble(), playerName)
                }
                else
                    endGame((count / questionList.size).toDouble(), "No Name Provided")
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

    private fun checkAnswer(): Int {
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
        return correct
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

