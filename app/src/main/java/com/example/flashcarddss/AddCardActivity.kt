package com.example.flashcarddss

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)


        findViewById<View>(R.id.myReturnBtn).setOnClickListener {
            val finished = Intent(this, MainActivity::class.java)
            startActivity(finished)

        }

        findViewById<View>(R.id.SaveBtn).setOnClickListener {
            Intent(this, MainActivity::class.java)

            val data = Intent()


            data.putExtra(
                "QUESTIONKEY",
                findViewById<EditText>(R.id.editQuestion).text.toString()
            )

            data.putExtra(
                "ANSWERKEY",
                findViewById<EditText>(R.id.editAnswer).text.toString()
            )

            setResult(RESULT_OK, data)

            finish()

        }

    }

}