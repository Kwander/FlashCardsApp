package com.example.flashcarddss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    lateinit var flashcardDatabase: FlashcardDatabase
    var currCardDisplayedIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flashcardDatabase = FlashcardDatabase(this)
        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        var allFlashcards = mutableListOf<Flashcard>()

        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }

        allFlashcards = flashcardDatabase.getAllCards().toMutableList()


        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (data != null) { // Check that we have data returned
                val questionString = data.getStringExtra("QUESTIONKEY")
                val answerString = data.getStringExtra("ANSWERKEY")


                flashcardQuestion.text = questionString
                flashcardAnswer.text = answerString

                if (!questionString.isNullOrEmpty() && !answerString.isNullOrEmpty()) {
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                }
            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        val nextButton = findViewById<ImageView>(R.id.nextButton)
        nextButton.setOnClickListener {
            if (allFlashcards.isEmpty()) {
                // early return so that the rest of the code doesn't execute
                return@setOnClickListener
            }

            currCardDisplayedIndex++

            if (currCardDisplayedIndex >= allFlashcards.size) {
                // go back to the beginning
                currCardDisplayedIndex = 0
            }

            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            val question = allFlashcards[currCardDisplayedIndex].question
            val answer = allFlashcards[currCardDisplayedIndex].answer

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
        }

        val addQuestionButton = findViewById<ImageView>(R.id.myBtn)
        addQuestionButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }


        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.GONE
            flashcardAnswer.visibility = View.VISIBLE
        }


        flashcardAnswer.setOnClickListener {
            flashcardQuestion.visibility = View.VISIBLE
            flashcardAnswer.visibility = View.GONE
        }
    }
}