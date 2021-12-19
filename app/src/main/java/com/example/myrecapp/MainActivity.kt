package com.example.myrecapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyVM::class.java)}

    private lateinit var etTitle: EditText
    lateinit var etAuthor: EditText
    lateinit var etIngredients: EditText
    lateinit var etInstructions: EditText
    lateinit var save: Button
    lateinit var view: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etTitle = findViewById(R.id.editTextTitle)
        etAuthor = findViewById(R.id.editTextAuthor)
        etIngredients = findViewById(R.id.editTextIngredients)
        etInstructions = findViewById(R.id.editTextInstructions)
        view = findViewById(R.id.viewBtn)
        save = findViewById(R.id.addusserBtn)
        save.setOnClickListener {
            var title = etTitle.text.toString()
            var author = etAuthor.text.toString()
            var ingredients = etIngredients.text.toString()
            var instructions = etInstructions.text.toString()
            if (title.isNotEmpty() && author.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty())
            {
                myViewModel.add(Recipe("",title,author,ingredients,instructions))

                Toast.makeText(this, "New Recipe is added to your list!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please Enter a Full Recipe", Toast.LENGTH_LONG).show()
            }
            etTitle.setText("")
            etAuthor.setText("")
            etIngredients.setText("")
            etInstructions.setText("")

        }
        view.setOnClickListener {
            val intent = Intent(this, ViewRecpe::class.java)
            startActivity(intent)
        }

    }
}